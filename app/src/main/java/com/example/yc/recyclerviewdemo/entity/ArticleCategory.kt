package com.example.yc.recyclerviewdemo.entity

/**
 * @author:    YC
 * @date:    2019/4/1 0001
 * @time:    21:48
 *@detail:
 */

/**
 * 文章类型，以及每种类型下的子类型和数据
 *  {历史，{"宋",100}，{"元"，200}}
 *  {军事，{"中国",1400}，{"日本"，230}}
 */
class ArticleCategory{

    var category = ""
    var articleList : List<Article>? = null

    // 每种子类型的名称，数据
    class Article(var name:String,var size:Int)
}