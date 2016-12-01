public class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, Set<String> wordList) {
      //exclude corner case
      //TODO in practical usage. So far no need in this question.

      //initialize result
      List<List<String>> result = new ArrayList<List<String>>();

      //initialize vairables needed to find all shorted paths
      int shorted;//update when first shorted path is found. once updated, stopping add new word to wordQueue
      boolean STOP_ADD = false;
      LinkedList<String> wordQueue = new LinkedList<String>();
      HashTable<String, Integer> lenTable = new HashTable<String, Integer>();
      HashTable<String, List<String> > pathTable = new HashTable<String, List<String>>();//<curWord, prevWordList>

      //find all shorted paths
      wordQueue.add(beginWord);
      lenTable.add(beginWord, 2);
      pathTable.add(beginWord, null);
      while( !wordQueue.isEmpty()){
        String word = wordQueue.remove();
        int len = lenTable.get(word);
        if(isMatch(word, endWord)){
          if(! STOP_ADD){
            updateResult(endWord, word, pathTable, result);
            shorted = len;
            STOP_ADD = true;
          }else{
            if(len == shorted) updateResult(endWord, word, pathTable, result);
            else break;
          }
        }else{
          if(! STOP_ADD){
            for(int j = 0; j < word.length(); j++){
                char[] charArr = word.toCharArray();
                for(char ch = 'a'; ch < 'z'; ch++){
                    charArr[j] = ch;
                    String check = new String(charArr);
                    if(!check.equals(word) && wordList.contains(check)){
                        wordQueue.add(check);
                        lenTable.add(check, len + 1);
                        pathTable.add(check, new ArrayList<String>({word}));
                        wordList.remove(check);//dynamically shrink size of wordList set
                    }
                    if(!check.equals(word) && !wordList.contains(check) && lenTable.get(check) == len + 1){
                      pathTable.get(check).add(word);
                    }
                }
            }
          }
        }
      }

      //return result;
      return result;
    }

    //TODO updateResult
    public void updateResult(String endWord, String word, HashTable<String, List<String>> pathTable, List<List<String>> result){
        List<String> cur = new ArrayList<String>();
        cur.add(endWord);
        cur.add(word);
        update(cur, pathTable, result);
    }
    public void update(List<String> cur, HashTable<String, List<String>> pathTable, List<List<String>> result){
      String word = cur.get(cur.size() - 1);
      if(pathTable.get(word) == null){
        List<String> solution = (ArrayList<String>)cur.clone();
        Collections.reverse(solution);
        result.add(solution);
      }
      else{
        List<String> prevList = pathTable.get(word);
        for(int i = 0; i < prevList.size(); i++){
          cur.add(prevList.get(i));
          update(cur, pathTable, result);
          cur.remove(cur.size() - 1);
        }
      }
    }

    public boolean isMatch(String word1, String word2){
      int count = 0;
      for(int i = 0; i < word1.length(); i++){
        if(word1.charAt(i) != word2.charAt(i)) count++;
      }
      if(count == 1) return true;
      else return false;

    }
}