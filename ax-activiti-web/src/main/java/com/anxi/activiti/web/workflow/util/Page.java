/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.anxi.activiti.web.workflow.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 *
 * @param <T>
 * @author ThinkGem
 * @version 2013-7-2
 */
public class Page<T> {

    private int pageNo = 1; // 当前页码
    private int pageSize; // 页面大小，设置为“-1”表示不进行分页（分页无效）

    private long count;// 总记录数，设置为“-1”表示不查询总数

    private int first;// 首页索引
    private int last;// 尾页索引
    private int prev;// 上一页索引
    private int next;// 下一页索引

    private boolean firstPage;//是否是第一页
    private boolean lastPage;//是否是最后一页

    private int length = 8;// 显示页面长度
    private int slider = 1;// 前后显示页面长度

    private List<T> list = new ArrayList<T>();


    private String funcName = "page"; // 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。

    private String funcParam = ""; // 函数的附加参数，第三个参数值。


    /**
     * 构造方法
     *
     * @param pageNo   当前页码
     * @param pageSize 分页大小
     * @param count    数据条数
     * @param list     本页数据对象列表
     */
    public Page(int pageNo, int pageSize, long count, List<T> list) {
        this.setCount(count);
        this.setPageNo(pageNo);
        this.pageSize = pageSize;
        this.setList(list);
    }

    /**
     * 初始化参数
     */
    public void initialize() {

        //1
        this.first = 1;

        this.last = (int) (count / (this.pageSize < 1 ? 20 : this.pageSize) + first - 1);

        if (this.count % this.pageSize != 0 || this.last == 0) {
            this.last++;
        }

        if (this.last < this.first) {
            this.last = this.first;
        }

        if (this.pageNo <= 1) {
            this.pageNo = this.first;
            this.firstPage = true;
        }

        if (this.pageNo >= this.last) {
            this.pageNo = this.last;
            this.lastPage = true;
        }

        if (this.pageNo < this.last - 1) {
            this.next = this.pageNo + 1;
        } else {
            this.next = this.last;
        }

        if (this.pageNo > 1) {
            this.prev = this.pageNo - 1;
        } else {
            this.prev = this.first;
        }

        //2
        if (this.pageNo < this.first) {// 如果当前页小于首页
            this.pageNo = this.first;
        }

        if (this.pageNo > this.last) {// 如果当前页大于尾页
            this.pageNo = this.last;
        }

    }

    /**
     * 默认输出当前分页标签
     * <div class="page">${page}</div>
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        if (pageNo == first) {// 如果是首页
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">&#171; 上一页</a></li>\n");
        } else {
            sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName + "(" + prev + "," + pageSize + ",'" + funcParam + "');\">&#171; 上一页</a></li>\n");
        }

        int begin = pageNo - (length / 2);

        if (begin < first) {
            begin = first;
        }

        int end = begin + length - 1;

        if (end >= last) {
            end = last;
            begin = end - length + 1;
            if (begin < first) {
                begin = first;
            }
        }

        if (begin > first) {
            int i = 0;
            for (i = first; i < first + slider && i < begin; i++) {
                sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName + "(" + i + "," + pageSize + ",'" + funcParam + "');\">"
                        + (i + 1 - first) + "</a></li>\n");
            }
            if (i < begin) {
                sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
            }
        }

        for (int i = begin; i <= end; i++) {
            if (i == pageNo) {
                sb.append("<li class=\"active\"><a href=\"javascript:\">" + (i + 1 - first)
                        + "</a></li>\n");
            } else {
                sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName + "(" + i + "," + pageSize + ",'" + funcParam + "');\">"
                        + (i + 1 - first) + "</a></li>\n");
            }
        }

        if (last - end > slider) {
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
            end = last - slider;
        }

        for (int i = end + 1; i <= last; i++) {
            sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName + "(" + i + "," + pageSize + ",'" + funcParam + "');\">"
                    + (i + 1 - first) + "</a></li>\n");
        }

        if (pageNo == last) {
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">下一页 &#187;</a></li>\n");
        } else {
            sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName + "(" + next + "," + pageSize + ",'" + funcParam + "');\">"
                    + "下一页 &#187;</a></li>\n");
        }

        sb.append("<li class=\"disabled controls\"><a href=\"javascript:\">当前 ");
        sb.append("<input id=\"pageSelect\" type=\"text\" min=\"" + first + "\" max=\"" + last + "\" value=\"" + pageNo + "\" onkeypress=\"var e=window.event||event;var c=e.keyCode||e.which;if(c==13)");
        sb.append(funcName + "(this.value," + pageSize + ",'" + funcParam + "');\" onclick=\"this.select();\"/> / ");
        sb.append("<input type=\"text\" value=\"" + pageSize + "\" onkeypress=\"var e=window.event||event;var c=e.keyCode||e.which;if(c==13)");
        sb.append(funcName + "(" + 1 + ",this.value,'" + funcParam + "');\" onclick=\"this.select();\"/> 条，");
        sb.append("共 " + count + " 条</a></li>\n");
        sb.insert(0, "<ul>\n").append("</ul>\n");
        sb.append("<div style=\"clear:both;\"></div>");
        return sb.toString();
    }

    /**
     * 获取分页HTML代码
     */
    public String getHtml() {
        return toString();
    }

    /**
     * 获取设置总数
     */
    public long getCount() {
        return count;
    }

    /**
     * 设置数据总数
     */
    public void setCount(long count) {
        this.count = count;
        if (pageSize >= count) {
            pageNo = 1;
        }
    }

    /**
     * 获取当前页码
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页码
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 获取页面大小
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置页面大小
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? 10 : pageSize;
    }

    /**
     * 首页索引
     */
    @JsonIgnore
    public int getFirst() {
        return first;
    }

    /**
     * 尾页索引
     */
    @JsonIgnore
    public int getLast() {
        return last;
    }

    /**
     * 获取页面总数
     *
     * @return getLast();
     */
    @JsonIgnore
    public int getTotalPage() {
        return getLast();
    }

    /**
     * 是否为第一页
     */
    @JsonIgnore
    private boolean isFirstPage() {
        return firstPage;
    }

    /**
     * 是否为最后一页
     */
    @JsonIgnore
    private boolean isLastPage() {
        return lastPage;
    }

    /**
     * 上一页索引值
     */
    @JsonIgnore
    public int getPrev() {
        if (isFirstPage()) {
            return pageNo;
        } else {
            return pageNo - 1;
        }
    }

    /**
     * 下一页索引值
     */
    @JsonIgnore
    public int getNext() {
        if (isLastPage()) {
            return pageNo;
        } else {
            return pageNo + 1;
        }
    }

    /**
     * 获取本页数据对象列表
     *
     * @return List<T>
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置本页数据对象列表
     */
    public Page<T> setList(List<T> list) {
        this.list = list;
        initialize();
        return this;
    }

    /**
     * 获取点击页码调用的js函数名称
     * function ${page.funcName}(pageNo){location="${ctx}/list-${category.id}${urlSuffix}?pageNo="+i;}
     */
    @JsonIgnore
    public String getFuncName() {
        return funcName;
    }

    /**
     * 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。
     *
     * @param funcName 默认为page
     */
    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }


}
