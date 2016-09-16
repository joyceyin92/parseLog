import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Solution {
	
	public static int arrayReduction2(int[] nums){
		PriorityQueue<Integer> queue  = new PriorityQueue<>();
		for(int num : nums){
			queue.add(num);
		}
		int sum = 0;
		while(queue.size()>1){
			int a = queue.poll();
			int b = queue.poll();
			sum += (a+b);
			queue.offer(a+b);
		}
		return sum;
	}
	
	
	public static int segment(int x, int[] arr){
		int max = Integer.MIN_VALUE;
		LinkedList<Integer> deque = new LinkedList<Integer>();
		for(int i = 0; i < arr.length; i++){
			if(!deque.isEmpty() && i > deque.peek()+x-1){
				deque.removeFirst();
			}
			while(!deque.isEmpty() && arr[i] < arr[deque.getLast()]){
				deque.removeLast();
			}
			deque.offer(i);
			if(i >= x-1){
				int min = arr[deque.getFirst()];
				max = Math.max(max, min);
			}
		}
		return max;
	}
	
	public static class Identifier{
		String name;
		int number;
		Identifier(String name, int number){
			this.name = name;
			this.number = number;
		}
	}
	
	public static String[] royalNames(String[] names){
		PriorityQueue<Identifier> queue = new PriorityQueue<>(new Comparator<Identifier>(){
			@Override
			public int compare(Identifier a, Identifier b){
				if(a.name.compareTo(b.name)!=0){
					return a.name.compareTo(b.name);
				}else{
					return a.number - b.number;
				}
			}
		});
		
		HashMap<Identifier, String> map = new HashMap<>();
		for(String s : names){
			String[] id = s.split(" ");
			Identifier cur = new Identifier(id[0],romanToInt(id[1]));
			queue.offer(cur);
			map.put(cur, s);
		}
		String[] result = new String[names.length];
		int i = 0;
		while(!queue.isEmpty()){
			Identifier cur = queue.poll();
			result[i] = map.get(cur);
			i++;
		}
		return result;
	}
	
	public static int romanToInt(String s){
		HashMap<Character, Integer> map = new HashMap<>();
		map.put('I', 1);    map.put('V', 5);
		map.put('X', 10);   map.put('L', 50);
		int num = 0;
		for(int i = 0; i < s.length()-1; i++){
			if(map.get(s.charAt(i)) < map.get(s.charAt(i+1))){
				num -= map.get(s.charAt(i));
			}else{
				num += map.get(s.charAt(i));
			}
		}
		num += map.get(s.charAt(s.length()-1));
		return num;
	}
	public static class Candidate{
		String name;
		int vote;
		Candidate(String name, int vote){
			this.name = name;
			this.vote = vote;
		}
	}
	public static String electionWinner(String[] names){
		HashMap<String, Integer> map = new HashMap<>();
		for(String name : names){
			/*if(map.containsKey(name)){
				map.put(name, map.get(name)+1);
			}else{
				map.put(name, 1);
			}*/
			map.put(name,map.getOrDefault(name, 0)+1);
		}
		PriorityQueue<Candidate> queue = new PriorityQueue<>(new Comparator<Candidate>(){
			@Override
			public int compare(Candidate a, Candidate b){
				if(a.vote!=b.vote) return b.vote - a.vote;
				else return 0 - a.name.compareTo(b.name);
			}
		});
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			Candidate cur = new Candidate(entry.getKey(), entry.getValue());
			queue.offer(cur);
		}
		return queue.peek().name;
	}
	
	public static String mergeString(String a, String b){
		int i = 0;
		int j = 0;
		StringBuilder c = new StringBuilder();
		while(i < a.length() && i < b.length()){
			c.append(a.charAt(i)).append(b.charAt(i++));
		}
		if(i>=a.length() && i < b.length()) c.append(b.substring(i));
		else if(i>=b.length() && i < a.length()) c.append(a.substring(i));
		return c.toString();
	}
	public static class Entity{
        int x;
        int y;
        Entity(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static int nonDominatable(int[][] arr){
        Entity[] set = new Entity[arr.length];
        for(int i = 0;i < arr.length; i++){
            set[i] = new Entity(arr[i][0], arr[i][1]);
        }
        Arrays.sort(set, new Comparator<Entity>(){
            @Override
            public int compare(Entity a, Entity b){
                if(a.x != b.x) return a.x - b.x;
                else return a.y - b.y;
            }
        });
        int count = 1;
        for(int j = 0; j < set.length-1; j++){
            for(int k = j+1; k < set.length; k++){
                if(set[j].x < set[k].x && set[j].y < set[k].y)
                    break;
                if(k==set.length-1) count++;
            }
        }
        return count;
    }
    
    static List<String> parseLines(List<String> lines) {
    	String[] firstLine = lines.get(0).split(" ");
    	String src = firstLine[0];
    	String des = firstLine[1];
    	HashMap<String, String[]> map = new HashMap<>();
    	for(int i = 1; i < lines.size();i++){
    		String[] curLine = lines.get(i).split(":");
    		String vertex = curLine[0].trim();
    		String[] list = curLine[1].trim().split(" ");
    		map.put(vertex, list);
    	}
    	HashSet<String> visited = new HashSet<>();
    	List<String> res = new ArrayList<String>();
    	dfs(src, des, "", res, visited, map);
    	return res;
    }
    static void dfs(String src, String des, String path, List<String> res, HashSet<String> visited, HashMap<String, String[]> map){
    	String newPath = path + src;
    	visited.add(src);
    	if(src.equals(des)){
    		res.add(newPath);
    	}
    	else if(map.containsKey(src)){
    		for(String next : map.get(src)){
        		if(next!=null){
        			if(!visited.contains(next)){
            			dfs(next, des, newPath, res, visited, map);
            		}
        		}
        	}
    	}
    	visited.remove(src);
    }
  
    static String parseLogs(String[] lines) {
    	long startTime = 0;
    	long endTime = 0;
    	boolean hasConnected = false;
    	long lastConnected = 0;
    	long totalConnect = 0;
    	for(int i = 0; i < lines.length; i++){
    		String[] curLine = lines[i].split("::");
    		String event = curLine[1].trim();
    		//System.out.println(event);
    		String time = curLine[0].trim().substring(1, curLine[0].length()-1);
    		//System.out.println(time);
    		if(event.equals("START")){
    			startTime = getDateFormat(time).getTime();
    			//System.out.println(startTime);
    		}
    		if(event.equals("SHUTDOWN")){
    			endTime = getDateFormat(time).getTime();
    			//System.out.println(endTime);
    		}
    		if(event.equals("CONNECTED")){
    			hasConnected = true;
    			lastConnected = getDateFormat(time).getTime();
    		}
    		if(event.equals("DISCONNECTED")||event.equals("SHUTDOWN")){
    			if(hasConnected == true){
    				totalConnect += (getDateFormat(time).getTime() - lastConnected);
    				hasConnected = false;
    			}
    		}
    	}
    	double percent = (double) totalConnect / (endTime - startTime) * 100;
    	return String.format("%d%s", (int) percent, "%");

    }
    static Date getDateFormat(String input){
    	SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy-hh:mm:ss");
    	Date t = new Date();
        try {
           t = df.parse(input); 
           //System.out.println(t); 
        }catch (ParseException e) {
        }
        return t;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int result = arrayReduction({1,2,3});
		//int[] nums = {4,1,2,9,5,11,3};
		//System.out.println(arrayReduction2(nums));
		//int[] arr = {9,2,-1,-5,10};
		//System.out.println(segment(3,arr));
		//System.out.println(romanToInt("XXIX"));
		/*String[] names = {"Louis IX", "Louis VIII","Anna XI","Lucifier V","Anna XXV"};
		String[] res = royalNames(names);
		for(String name : res)
		System.out.println(name);*/
		//String[] names = {"Alex", "Michael","Harry","Dave","Michael","Victor","Harry","Alex","Mary","Mary"};
		//System.out.println(electionWinner(names));
		//System.out.println(mergeString("abcxyz", "defg"));
		/*List<String> lines = new ArrayList<String>();
		lines.add("A J");
		lines.add("A : B C");
		lines.add("B : C E G");
		lines.add("C : A F");
		lines.add("D : C J");
		lines.add("F : B H");
		lines.add("G : C D");
		lines.add("H : A B F I");
		lines.add("I : B");
		
		List<String> lines2 = new ArrayList<String>();
		lines2.add("A D");
		lines2.add("A : B C");
		lines2.add("B : A C D");
		lines2.add("C : D");
		List<String> res = parseLines(lines2);
		for(String s : res) System.out.println(s);*/
		
		String[] lines = {"(11/01/2015-04:00:00) :: START",
				"(11/01/2015-04:00:00) :: CONNECTED",
				"(11/01/2015-04:30:00) :: DISCONNECTED",
				"(11/01/2015-04:45:00) :: CONNECTED",
				"(11/01/2015-05:00:00) :: SHUTDOWN"};
		//System.out.println(lines.length);
		System.out.println(parseLogs(lines));				
	}

}

