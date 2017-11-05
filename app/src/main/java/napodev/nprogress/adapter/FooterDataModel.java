package napodev.nprogress.adapter;

/**
 * Created by opannapo on 11/17/16.
 */
public class FooterDataModel {
    private boolean hasnNextPage;
    private int currentPage;
    private int totalPage;


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isHasnNextPage() {
        return hasnNextPage;
    }

    public void setHasnNextPage(boolean hasnNextPage) {
        this.hasnNextPage = hasnNextPage;
    }
}
