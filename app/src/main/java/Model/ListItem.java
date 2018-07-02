package Model;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;
/**
 * POJO Created by SubhasishNath on 6/11/2018.
 */
//public class ListItem implements Serializable
public class ListItem  implements Serializable {
    @SerializedName("id")
    long id;
    String post;
    int status;
    String createdAt;
    String Title;
    String Content;
    String Description;
    String postURL;
    String postImg;
    String postExcerpt;
    public String getPostExcerpt() {
        return postExcerpt;
    }

    public void setPostExcerpt(String postExcerpt) {
        this.postExcerpt = postExcerpt;
    }


    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
    public String getPostURL() {
        return postURL;
    }
    public void setPostURL(String postURL) {
        this.postURL = postURL;
    }
    public String getPostImg() {
        return postImg;
    }
    public void setPostImg(String postImg) {
        this.postImg = postImg;
    }
    public String getPost() {
        return post;
    }
    public void setPost(String post) {
        this.post = post;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}