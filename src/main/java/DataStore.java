public interface DataStore {

  public Boolean existsInStore(String uri);
  public String read(String uri);

}