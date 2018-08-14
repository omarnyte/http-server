public interface DataStore {

  public Boolean isDirectory(String uri);
  public String[] listContent();
  public Boolean existsInStore(String uri);
  public byte[] readFile(String uri);
  public String getFileType(String uri);
  public void postFile (String uri, byte[] content);
  public boolean deleteFile(String uri);


  public Boolean isDirectory(String uri);
  public DataStore createSubdirectoryStore(String uri) throws NonexistentDirectoryException;
}