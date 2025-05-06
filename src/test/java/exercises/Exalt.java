package exercises;

import org.junit.Test;

import java.util.*;

public class Exalt {
  // TODO: Coming soon

    @Test
    public void anagrams(){

        List<String> words = new ArrayList<>();
        //abc,bca,listen, cba, add, daa, silent""
        words.addAll(Arrays.asList("abc","bca","listen", "cba", "add", "daa", "silent"));
       // words.forEach(System.out::println);


        Map<String, ArrayList<String>> res = new HashMap<>();

        for(String word: words){
            char[] c =  word.toCharArray();
            Arrays.sort(c);
            res.computeIfAbsent(c.toString() , x -> new ArrayList<>()).add(word) ;
        }

        //res.entrySet().forEach(x -> x.getKey());
        for(Map.Entry entry :  res.entrySet()){
            System.out.println("Key "+ entry.getKey().toString());
             ((ArrayList) entry.getValue())
                    .stream().forEach(System.out::println);
        }

    }

    @Test
    public void anagramss(){
        //List<String> words = new ArrayList<>();
        //abc,bca,listen, cba, add, daa, silent""
        //words.addAll(Arrays.asList("abc","bca","listen", "cba", "add", "daa", "silent"));
        // words.forEach(System.out::println);
        Map<String, ArrayList<String>> res = new HashMap<>();
        List<String> words = Arrays.asList("abc","bca","listen", "cba", "add", "daa", "silent");
        for(String str: words){
            char[] c = str.toCharArray();
            Arrays.sort(c);
            String s =  String.valueOf(c);
            if(!res.containsKey(s)){
                res.put(s, new ArrayList<>());
            }
            res.get(s).add(str);
        }

        for(Map.Entry entry :  res.entrySet()){
            System.out.println("Key "+ entry.getKey().toString());
            ((ArrayList) entry.getValue())
                    .stream().forEach(System.out::print);
        }
    }
}
