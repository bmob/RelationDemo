package cn.bmob.relationdemo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by nothing on 2016/11/14.
 */

public class Post extends BmobObject {
    private String title;
    private String content;
    // 帖子的发布者 是一对一 帖子属于某个用户
    private MyUser author;
    private BmobFile image;
    // 多对多 喜欢该帖子的所有用户(用户也可以喜欢多个帖子)
    private BmobRelation likes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }
}
