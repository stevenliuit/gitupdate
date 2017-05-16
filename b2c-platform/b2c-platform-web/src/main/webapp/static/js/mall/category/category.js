/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: category.java project: jcloud-b2c-platform
 * @creator: lidongxing
 * @date: 2017/2/25
 */

/**
 * @description: 楼层
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-13 21:18
 * @lastdate:
 */
var category = window.category || {};

category.tree = (function(){
    var maxLevel = 1;
    var gen = function(catMap, max_Level){
        maxLevel = max_Level;
        for(var level = 1; level <= maxLevel; level++){
            for(var idLevel in catMap){
                var category = catMap[idLevel];
                var id = idLevel.split('-')[0];
                var ilevel = idLevel.split('-')[1];
                if(level == ilevel){
                    genCatTr(level, category);
                }
            }
        }
    };

    var genCatTr = function(level, category){
        var catTrTemplate = $("#j_category_body_template .CatTr_"+level);
        var catTr = catTrTemplate.clone();
        catTr.attr("id", category["id"]);
        catTr.attr("level", level);
        catTr.attr("sort", category["sortNum"]);
        catTr.attr("pid", category["parentId"]);
        catTr.find("[name='j_category_name']").attr("value", category["name"]);
        catTr.find("[name='j_category_name']").attr("readonly", true);
        catTr.find("[name='j_category_name']").attr("id", category["id"]+"-" + level);
        catTr.find(".edit_category").attr("data", "edit-"+category["parentId"]+"-"+category["id"]);
        catTr.find(".move").attr("data", category["id"]+"-"+category["parentId"]+"-"+category["sortNum"]);
        $(catTr.find("td").get(0)).text(category["id"]);
        if(catTr.find(".toAddAd")){
            catTr.find(".toAddAd").attr("href","/mall/mallAd/toAdIndex?type=3&principalId="+category["id"]);
        }
        if(catTr.find("a[name='add-item']")){
            catTr.find("a[name='add-item']").attr("href","/mall/relatedItem/main/" + clientTypeName +"/category?principalId="+category["id"]+"&categoryName="+category["name"]);
        }
        if(level==1 &&  $("#j_category_body").children().length==0){
            $(catTr).appendTo($("#j_category_body"));
        }else{
            var beforeCat = getBeforeCategory(level, category, catTr);
            //if(beforeCat.attr("sort")>catTr.attr("sort")){
                //beforeCat.after(catTr);
            //}else{
                //beforeCat.before(catTr);
            //}
        }
        genAddCatTr(level, category, catTr);
        //$("#j_category_body").appendChild(catTrTemplate);
    };

    var genAddCatTr = function(level, category, catTr){
        if(!level || level<1 || level> maxLevel){
            return;
        }
        var addCatTrTemplate = $("#j_category_body_template .AddCatTr_"+level);
        var catAddTr = addCatTrTemplate.clone();
        catAddTr.find(".edit_category").attr("data", "add-"+category["id"]+"-0");
        catAddTr.attr("pid", category["parentId"]);
        catAddTr.attr("level", level);
        catTr.after(catAddTr);
        return catAddTr;
    }

    var getBeforeCategory = function(level, category, catTr){
        var array = $("tr:[name='CatTr_"+level+"'][pid='"+category["parentId"]+"']");
        var beforeCat;
        if(!array || array.length==0){
            beforeCat = $("#"+category["parentId"]) ;
        }else{
            for(var index=0;index<array.length; index++){
                //var arrCat = $(array[index]);
                if($(array[index]).attr("sort")>category["sortNum"]){
                    break;
                }
                beforeCat = $(array[index]);
            }
        }
        if(beforeCat){
            if(beforeCat.attr("level")==catTr.attr("level")){
                //最后一级，不需要越过"添加子分类"
                if(level==maxLevel){
                    beforeCat.after(catTr);
                }else{
                    //越过"添加子分类"
                    beforeCat.next().after(catTr);
                }
            }else{
                beforeCat.find(".control-line .caret").removeClass("before").addClass("develop").show();
                beforeCat.after(catTr);
            }
        }else{
            if(level==1){
                //$(catTr).appendTo($("#j_category_body"));;
                $("#j_category_body tr:first-child").before(catTr);
            }else{
                $("#"+category["parentId"]).after(catTr);
            }
        }
        //return beforeCat;

        /**
        if(level == 1){
            return $("#j_category_body tr:last-child");
        }else{
            return $("#"+category["parentId"]);
        }
        */
    }

    return {
        gen : gen
    }
})();