package models.entity;

import models.api.Ownerable;
import siena.*;

import java.util.Date;
import java.util.List;

import static utils.StringUtil.nvl;

public class Say extends Model implements Ownerable<String> {
    @Id
    public Long id;
    //用户名(标识)
    public String username;
    public String who;
    //说的内容
    @Max(200)
    @NotNull
    public String content;
    //创建时间
    public Date createAt = new Date();
    //发布工具
    public String createTools = "tangqiong.com";
    //回复对象
    @Index("target_index")
    public Say target;
    public String replyWho;
    //被回复数
    public Integer replys = 0;

    @Filter("target")
    public Query<Say> replies;

    public Say(String user, String content) {
        this(user, content, null);
    }

    public Say(String user, String content, Say target) {
        this.username = user;
        this.content = content;
        this.target = target;
        User theUser = User.get(username);
        this.who = nvl(theUser.nickname, theUser.username);
        if (target != null) {
            User targetUser = User.get(target.username);
            this.replyWho = nvl(targetUser.nickname, targetUser.username);
        }
    }

    public List<Say> replies() {
        return replies.fetch();
    }

    public String owner() {
        return this.username;
    }

    public String toString() {
        return "say{" + username + "}";
    }
}
