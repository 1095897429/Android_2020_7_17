package com.liangzai.myjavaapplication.bean;

import com.liangzai.myjavaapplication.net.base.BaseBean;

import java.util.List;

/**
 * @author zhouliang
 * 版本 1.0
 * 创建时间 2020/7/20
 * 描述:
 */
public class HomeBean extends BaseBean {
    private List<IssueList> issueList;
    private String nextPageUrl;
    private long nextPublishTime;
    private String newestIssueType;
    private String dialog;

    public class Data {
        private String dataType;
        private String text;
        private String font;
        private String adTrack;
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public String getDataType() {
            return dataType;
        }

        public void setText(String text) {
            this.text = text;
        }
        public String getText() {
            return text;
        }

        public void setFont(String font) {
            this.font = font;
        }
        public String getFont() {
            return font;
        }

        public void setAdTrack(String adTrack) {
            this.adTrack = adTrack;
        }
        public String getAdTrack() {
            return adTrack;
        }

    }

    public class ItemList {
        private String type;
        private Data data;
        private String tag;
        private int id;
        private int adIndex;
        public void setType(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }

        public void setData(Data data) {
            this.data = data;
        }
        public Data getData() {
            return data;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
        public String getTag() {
            return tag;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setAdIndex(int adIndex) {
            this.adIndex = adIndex;
        }
        public int getAdIndex() {
            return adIndex;
        }

    }

    public class IssueList {
        private long releaseTime;
        private String type;
        private long date;
        private long publishTime;
        private List<ItemList> itemList;
        private int count;
        public void setReleaseTime(long releaseTime) {
            this.releaseTime = releaseTime;
        }
        public long getReleaseTime() {
            return releaseTime;
        }

        public void setType(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }

        public void setDate(long date) {
            this.date = date;
        }
        public long getDate() {
            return date;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }
        public long getPublishTime() {
            return publishTime;
        }

        public void setItemList(List<ItemList> itemList) {
            this.itemList = itemList;
        }
        public List<ItemList> getItemList() {
            return itemList;
        }

        public void setCount(int count) {
            this.count = count;
        }
        public int getCount() {
            return count;
        }

    }


    public List<IssueList> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<IssueList> issueList) {
        this.issueList = issueList;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public long getNextPublishTime() {
        return nextPublishTime;
    }

    public void setNextPublishTime(long nextPublishTime) {
        this.nextPublishTime = nextPublishTime;
    }

    public String getNewestIssueType() {
        return newestIssueType;
    }

    public void setNewestIssueType(String newestIssueType) {
        this.newestIssueType = newestIssueType;
    }

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }
}
