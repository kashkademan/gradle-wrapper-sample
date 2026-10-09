package pro._21it.readlater.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"title", "author"})
public class CreateBookRequest {
    public String title;
    public String author;

    public CreateBookRequest() {
    }

    public CreateBookRequest(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
