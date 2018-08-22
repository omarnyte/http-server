public interface DataStore {

  public String[] listContent();
  public Boolean existsInStore(String uri);
  public byte[] readFile(String uri);
  public String getFileType(String uri);
  public void postFile (String uri, byte[] content);
  public boolean deleteFile(String uri);

}