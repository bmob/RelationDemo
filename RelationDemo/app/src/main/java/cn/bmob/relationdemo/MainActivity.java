package cn.bmob.relationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String TAG = "1114";
    String postId = "";
    String commentId = "";
    Button signUp;
    Button login;
    Button addOneToOneRelation;
    Button queryOneToOneRelation;
    Button updateOneToOneRelation;
    Button delOneToOneRelation;
    Button addOneToMoreRelation;
    Button queryOneToMoreRelation;
    Button addMoreToMoreRelation;
    Button queryMoreToMoreRelation;
    Button updateMoreToMoreRelation;
    Button delMoreToMoreRelation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        signUp = (Button) findViewById(R.id.signUp);
        login = (Button) findViewById(R.id.login);
        addOneToOneRelation = (Button) findViewById(R.id.addOneToOneRelation);
        queryOneToOneRelation = (Button) findViewById(R.id.queryOneToOneRelation);
        updateOneToOneRelation = (Button) findViewById(R.id.updateOneToOneRelation);
        delOneToOneRelation = (Button) findViewById(R.id.delOneToOneRelation);
        addOneToMoreRelation = (Button) findViewById(R.id.addOneToMoreRelation);
        queryOneToMoreRelation = (Button) findViewById(R.id.queryOneToMoreRelation);
        addMoreToMoreRelation = (Button) findViewById(R.id.addMoreToMoreRelation);
        queryMoreToMoreRelation = (Button) findViewById(R.id.queryMoreToMoreRelation);
        updateMoreToMoreRelation = (Button) findViewById(R.id.updateMoreToMoreRelation);
        delMoreToMoreRelation = (Button) findViewById(R.id.delMoreToMoreRelation);

        signUp.setOnClickListener(this);
        login.setOnClickListener(this);
        addOneToOneRelation.setOnClickListener(this);
        queryOneToOneRelation.setOnClickListener(this);
        updateOneToOneRelation.setOnClickListener(this);
        delOneToOneRelation.setOnClickListener(this);
        addOneToMoreRelation.setOnClickListener(this);
        queryOneToMoreRelation.setOnClickListener(this);
        addMoreToMoreRelation.setOnClickListener(this);
        queryMoreToMoreRelation.setOnClickListener(this);
        updateMoreToMoreRelation.setOnClickListener(this);
        delMoreToMoreRelation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUp:
                signUp();
                break;
            case R.id.login:
                login();

                break;
            case R.id.addOneToOneRelation:
                addOneToOne();

                break;
            case R.id.queryOneToOneRelation:
                queryOneToOne();

                break;
            case R.id.updateOneToOneRelation:
                updateOneToOne();

                break;
            case R.id.delOneToOneRelation:
                delOneToOne();

                break;
            case R.id.addOneToMoreRelation:
                addOneToMore();
                break;
            case R.id.queryOneToMoreRelation:
                queryOneToMore();
                break;
            case R.id.addMoreToMoreRelation:
                addMoreToMore();
                break;
            case R.id.queryMoreToMoreRelation:
                queryMoreToMore();
                break;
            case R.id.updateMoreToMoreRelation:
                updateMoreToMore();
                break;
            case R.id.delMoreToMoreRelation:
                delMoreToMore();
                break;
            default:
                break;
        }
    }

    /**
     * 删除多对多关联 -> 对帖子进行取消喜欢的操作
     */
    private void delMoreToMore() {
        Post post = new Post();
        post.setObjectId(postId);
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobRelation relation = new BmobRelation();
        // 取消喜欢也就是从relation中删除一个user对象
        relation.remove(user);
        // 重新设置指向
        post.setLikes(relation);
        post.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("ok ");
                } else {
                    Log.e(TAG, e.toString());
                }
            }
        });

    }

    /**
     * 修改多对多关联 -> 新增一个用户到post表的likes字段
     */
    private void updateMoreToMore() {
        Post post = new Post();
        post.setObjectId(postId);
        // 把用户B添加到post的likes字段中
        BmobRelation relation = new BmobRelation();
        MyUser user = new MyUser();
        user.setObjectId("59c80be08e");
        relation.add(user);
        // 多对多关联指向likes字段
        post.setLikes(relation);
        post.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("ok ");
                } else {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    /**
     * 查询多对多关联 -> 查询喜欢某个帖子的所有用户
     * 要用到addWhereRelatedTo方法
     */
    private void queryMoreToMore() {
        BmobQuery<MyUser>  query  = new BmobQuery<>();
        Post post = new Post();
        post.setObjectId(postId);
        // TODO 不友好
        query.addWhereRelatedTo("likes",new BmobPointer(post));
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e == null) {
                    toast("ok " + list.size());
                } else {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    /**
     * 添加多对多关联 -> 帖子和喜欢这个帖子的用户的关系
     * 一个帖子可以被多个用户喜欢 一个用户也能喜欢多个帖子
     */
    private void addMoreToMore() {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        Post post = new Post();
        post.setObjectId(postId);
        // 把当前用户添加到帖子的likes字段
        BmobRelation relation = new BmobRelation();
        // 把用户加到多对多关系中
        relation.add(user);
        // 多对多关联指向post的likes字段
        post.setLikes(relation);
        post.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("ok ");
                } else {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    /**
     * 查询某个帖子下的所有评论
     */
    private void queryOneToMore() {
        BmobQuery<Comment> query = new BmobQuery<>();
        Post post  = new Post();
        post.setObjectId(postId);
        // TODO 不友好
        query.addWhereEqualTo("post",new BmobPointer(post));
        // 希望查询到评论发布者的具体信息 用下内部查询
        // 这里稍复杂些 查询评论发布者的信息 和 帖子作者的信息 参见include的并列对象查询和内嵌对象的查询
        query.include("user,post.author");
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    toast("ok " + list.get(0).getContent());
                } else {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }


    /**
     * 添加一对多关联 -> 创建评论并关联评论和帖子
     */
    private void addOneToMore() {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        Post post = new Post();
        post.setObjectId(postId);
        Comment comment = new Comment();
        comment.setContent("不能同意更多");
        comment.setPost(post);
        comment.setAuthor(user);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    toast("ok ");
                    commentId = s;
                } else {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }
    /**
     * 删除一对一关联 -> 解除user和某个帖子的关联
     */
    private void delOneToOne() {
        Post post = new Post();
        post.remove("author");
        post.update(postId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("ok");
                } else {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    /**
     * 更新一对一关联 -> 把某个帖子的作者修改为另一用户
     */
    private void updateOneToOne() {
        Post post = new Post();
        MyUser user = new MyUser();
        user.setObjectId("59c80be08e");
        post.setAuthor(user);
        post.update(postId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("ok");
                } else {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    /**
     * 查询一对一关联 -> 查询当前用户发表的所有帖子
     */
    private void queryOneToOne() {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("author",user);
        query.order("-updatedAt");
        // 查询完post表后想把发布者的信息查询出来 要用内部查询
        query.include("author");
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e == null) {
                    toast("ok " + list.get(0).getContent());
                } else {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    /**
     * 添加一对一关联 -> 创建帖子
     */
    private void addOneToOne() {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        Post post = new Post();
        post.setContent("多看demo就知道，文档辅助效率高");
        post.setAuthor(user);
        post.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    toast("ok");
                    postId = s;
                } else {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    private void login() {
        MyUser user = new MyUser();
        user.setUsername("allen1114");
        user.setPassword("123");
        user.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser user, BmobException e) {
                if (e == null) {
                    toast("ok " + user.toString());
                } else {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    private void signUp() {
        MyUser user = new MyUser();
        user.setUsername("allen1114");
        user.setPassword("123");
        user.setAge(22);
        user.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser user, BmobException e) {
                if (e == null) {
                    toast("ok " + user.toString());
                } else {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }



    public void toast(String string) {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
    }
}
