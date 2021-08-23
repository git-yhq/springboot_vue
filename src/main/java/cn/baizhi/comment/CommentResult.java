package cn.baizhi.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResult { //对象 表示一个状态
    private String status;
    private String message;
    private Object data;

    //定义一个表示成功的状态的方法
    public static CommentResult success(String message,Object data){
        CommentResult cr = new CommentResult();
        cr.setStatus("100");
        cr.setMessage(message);
        cr.setData(data);
        return cr;
    }

    //定义一个表示失败的状态的方法
    public static CommentResult fail(String message,Object data){
        CommentResult cr = new CommentResult();
        cr.setStatus("104");
        cr.setMessage(message);
        cr.setData(data);
        return cr;
    }
}
