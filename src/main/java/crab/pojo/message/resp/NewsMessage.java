package crab.pojo.message.resp;

import java.util.List;

/**
 * �ı���Ϣ
 *
 */
public class NewsMessage extends BaseMessage {
    // ͼ����Ϣ����������Ϊ10������
    private int ArticleCount;
    // ����ͼ����Ϣ��Ϣ��Ĭ�ϵ�һ��itemΪ��ͼ
    private List<Article> Articles;

    private int FuncFlag;

    public int getFuncFlag() {
        return FuncFlag;
    }

    public void setFuncFlag(int funcFlag) {
        FuncFlag = funcFlag;
    }

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}
