package ajmitchell.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "movie_table")
public class Movie {

//    @PrimaryKey(autoGenerate = true)
//    private int movieId;

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    @Ignore
    private List<Result> results = null;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    public Movie() {}

    //@Embedded Result result; // ??? looking to have the table Movie have columns represented in Result. We'll see!

//    public int getMovieId() { return movieId; }
//
//    public void setMovieId(int id) { this.movieId = id; }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @Entity(tableName = "movie_results_table")
    public static class Result implements Parcelable {

        @PrimaryKey()
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("columnId")
        @Ignore
        private int columnId;

        @SerializedName("poster_path")
        @Expose
        private String posterPath;
        @SerializedName("adult")
        @Expose
        private Boolean adult;
        @SerializedName("overview")
        @Expose
        private String overview;
        @SerializedName("release_date")
        @Expose
        private String releaseDate;

        @SerializedName("genre_ids")
        @Expose
        @Ignore
        private List<Integer> genreIds = null;
        @SerializedName("original_title")
        @Expose
        private String originalTitle;
        @SerializedName("original_language")
        @Expose
        private String originalLanguage;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("backdrop_path")
        @Expose
        private String backdropPath;
        @SerializedName("popularity")
        @Expose
        private Double popularity;
        @SerializedName("vote_count")
        @Expose
        private Integer voteCount;
        @SerializedName("video")
        @Expose
        private Boolean video;
        @SerializedName("vote_average")
        @Expose
        private Double voteAverage;

        public Result() {
        }
        public int getColumnId() {
            return columnId;
        }

        public void setColumnId(int columnId) {
            this.columnId = columnId;
        }

        protected Result(Parcel in) {
            posterPath = in.readString();
            byte tmpAdult = in.readByte();
            adult = tmpAdult == 0 ? null : tmpAdult == 1;
            overview = in.readString();
            releaseDate = in.readString();
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            originalTitle = in.readString();
            originalLanguage = in.readString();
            title = in.readString();
            backdropPath = in.readString();
            if (in.readByte() == 0) {
                popularity = null;
            } else {
                popularity = in.readDouble();
            }
            if (in.readByte() == 0) {
                voteCount = null;
            } else {
                voteCount = in.readInt();
            }
            byte tmpVideo = in.readByte();
            video = tmpVideo == 0 ? null : tmpVideo == 1;
            if (in.readByte() == 0) {
                voteAverage = null;
            } else {
                voteAverage = in.readDouble();
            }
        }

        public static final Creator<Result> CREATOR = new Creator<Result>() {
            @Override
            public Result createFromParcel(Parcel in) {
                return new Result(in);
            }

            @Override
            public Result[] newArray(int size) {
                return new Result[size];
            }
        };

        public String getPosterPath() {
            return posterPath;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public Boolean getAdult() {
            return adult;
        }

        public void setAdult(Boolean adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public List<Integer> getGenreIds() {
            return genreIds;
        }

        public void setGenreIds(List<Integer> genreIds) {
            this.genreIds = genreIds;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public String getOriginalLanguage() {
            return originalLanguage;
        }

        public void setOriginalLanguage(String originalLanguage) {
            this.originalLanguage = originalLanguage;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBackdropPath() {
            return backdropPath;
        }

        public void setBackdropPath(String backdropPath) {
            this.backdropPath = backdropPath;
        }

        public Double getPopularity() {
            return popularity;
        }

        public void setPopularity(Double popularity) {
            this.popularity = popularity;
        }

        public Integer getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(Integer voteCount) {
            this.voteCount = voteCount;
        }

        public Boolean getVideo() {
            return video;
        }

        public void setVideo(Boolean video) {
            this.video = video;
        }

        public Double getVoteAverage() {
            return voteAverage;
        }

        public void setVoteAverage(Double voteAverage) {
            this.voteAverage = voteAverage;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(posterPath);
            dest.writeByte((byte) (adult == null ? 0 : adult ? 1 : 2));
            dest.writeString(overview);
            dest.writeString(releaseDate);
            if (id == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(id);
            }
            dest.writeString(originalTitle);
            dest.writeString(originalLanguage);
            dest.writeString(title);
            dest.writeString(backdropPath);
            if (popularity == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(popularity);
            }
            if (voteCount == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(voteCount);
            }
            dest.writeByte((byte) (video == null ? 0 : video ? 1 : 2));
            if (voteAverage == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(voteAverage);
            }
        }
    }
}
