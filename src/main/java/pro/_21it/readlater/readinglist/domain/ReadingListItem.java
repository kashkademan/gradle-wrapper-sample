package pro._21it.readlater.readinglist.domain;

public final class ReadingListItem {
    private final long id;
    private String title;
    private String author;
    private ReadingStatus status;
    private String externalId;
    private String comment;

    public ReadingListItem(
            long id, 
            String title, 
            String author,
            ReadingStatus status, 
            String externalId, 
            String comment) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
        this.externalId = externalId;
        this.comment = comment;
    }

    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }
    
    public String getAuthor() {
        return this.author;
    }

    public ReadingStatus getStatus() {
        return this.status;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public String getComment() {
        return this.comment;
    }

    public void update(
            String title, 
            String author,
            ReadingStatus status, 
            String externalId, 
            String comment) {
        this.title = title;
        this.author = author;
        this.status = status;
        this.externalId = externalId;
        this.comment = comment;
    }
}
