package cn.bmob.relationdemo;

import cn.bmob.v3.BmobObject;

/**
 * Created by nothing on 2016/11/14.
 */

public class Comment extends BmobObject {
    private String content;
    // 评论的作者 一对一
    private MyUser author;
    // 所评论的帖子 是一对多 一个评论对应一个帖子(一个帖子能有多个评论)
    private Post post;

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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
