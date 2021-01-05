package com.mp.demo;

/**
 * Desc:ajax请求的json结果
 */
public class JsonResult
{
    /**
     * 请求结果码
     */
    private Integer code = 0;

    /**
     * 请求结果
     */
    private Boolean success = true;

    /**
     * 请求错误信息
     */
    private String msg;

    /**
     * 结果数据
     */
    private Object data;

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(Boolean success)
    {
        this.success = success;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public static JsonResult newInstanceSuccess()
    {
        return newInstanceSuccess(null);
    }

    public static JsonResult newInstanceSuccess(Object data)
    {
        JsonResult jr = new JsonResult();
        jr.setData(data);
        return jr;
    }

    public static JsonResult newInstanceFail(String msg)
    {
        JsonResult jr = new JsonResult();
        jr.setSuccess(false);
        jr.setMsg(msg);
        return jr;
    }

    public static JsonResult newInstanceFail(String msg, Integer code)
    {
        JsonResult jr = new JsonResult();
        jr.setSuccess(false);
        jr.setMsg(msg);
        jr.setCode(code);
        return jr;
    }
}
