import java.io.*; 
import java.util.*; 

public class InvertedIndex
{
        
  // before witing index, assume and all index data will fit into memory
  Map<String, List<Integer>> index = new HashMap<>();
  List<String> documents = new ArrayList<String>();
  int docid = 0;
  
  // read and created index
  void create(File file) throws IOException 
  {
    String name = file.getName();
    // document ID
    documents.add(name);
    docid++;
    
    BufferedReader reader = null;
    try 
    {
      reader = new BufferedReader(new FileReader(file));
      String line;
      while((line = reader.readLine()) != null) 
      {
        String words[] = line.split("[^A-Za-z]"); // word tokenize
        for(String _word : words) 
        {
          String word = _word.toLowerCase();
          List<Integer> docs = index.get(word);
          if(docs == null) 
          {
            docs = new ArrayList<>();
            index.put(word, docs);
          }
          docs.add(docid);
        }
      }
    } 
    finally 
    {
      if(reader != null) 
      {
        reader.close();
      }
    }
          
  }
        
  // writes index back to file
  void write(File file) throws IOException 
  {
    BufferedWriter writer = null;
    try 
    {
      writer = new BufferedWriter(new FileWriter(file));
      // documents mapping
      for(int i=0;i<documents.size();i++) 
      {
        int id = i+1;
        writer.write(documents.get(i));
        writer.write(" ");
        writer.write(String.valueOf(id));
        writer.newLine();
      }
      // inverted index
      for(String word : index.keySet()) 
      {
        List<Integer> docs = index.get(word);
        writer.write(word);
        writer.write(" ");
        int s = docs.size();
        for(int i = 0; i < s - 1; i++) 
        {
          writer.write(String.valueOf(docs.get(i)));
          writer.write(" ");
        }
        writer.write(String.valueOf(docs.get(s - 1)));
        writer.newLine();
      }
    } 
    finally 
    {
      if(writer != null) 
      {
        writer.close();
      }
    }
  }

  // solution
  public void start() 
  {
    Scanner reader = new Scanner(System.in);
    System.out.println("Enter input directory: ");
    String input = reader.nextLine();
    System.out.println("Enter output file: ");
    String output = reader.nextLine();
    reader.close();
    
    try 
    {
      File files[] = new File(input).listFiles();
      System.out.println("Creating index.");
      
      for(File file : files) 
      {
        System.out.println("Processing: " + file);
        create(file); // create index
      }
      System.out.println("Writing.");
      // write it back
      write(new File(output));
      System.out.println("Done.");
    } 
    catch(IOException ex) 
    {
      // error
      throw new IllegalStateException(ex.getMessage(), ex);
    } 
    finally 
    {
      reader.close();
    }
  }

  public static void main (String args[]) throws Exception
  {    
    InvertedIndex ir = new InvertedIndex(); 
    ir.start(); 
  }
}
