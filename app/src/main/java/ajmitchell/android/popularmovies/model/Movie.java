package ajmitchell.android.popularmovies.model;

public class Movie {

    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private int mVoteAverage;
    private String mReleaseDate;

    public Movie() {
    }

    public Movie(String mTitle, String mPosterPath, String mOverview, int mVoteAverage, String mReleaseDate) {
        this.mTitle = mTitle;
        this.mPosterPath = mPosterPath;
        this.mOverview = mOverview;
        this.mVoteAverage = mVoteAverage;
        this.mReleaseDate = mReleaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public int getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(int mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }
}
