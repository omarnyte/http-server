public interface DataStore {

  public String listContent();
  public Boolean existsInStore(String uri);
  public String read(String uri);

}