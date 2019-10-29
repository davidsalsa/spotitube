package nl.han.dea.model;

public interface ITrack {
    public int getId();
    public void setId(int id);
    public String getTitle();
    public void setTitle(String title);
    public String getPerformer();
    public void setPerformer(String performer);
    public int getDuration();
    public void setDuration(int duration);
    public String getAlbum();
    public void setAlbum(String album);
    public int getPlaycount();
    public void setPlaycount(int playcount);
    public String getPublicationDate();
    public void setPublicationDate(String publicationDate);
    public String getDescription();
    public void setDescription(String description);
    public boolean isOfflineAvailable();
    public void setOfflineAvailable(boolean offlineAvailable);
}
