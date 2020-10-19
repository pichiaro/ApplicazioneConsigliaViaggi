package onclicklisteners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RatingBar;

public class RatingBarRatingSetter implements OnClickListener {

    private RatingBar ratingBar;
    private float rating;

    public RatingBarRatingSetter(RatingBar ratingBar, float rating) {
        this.ratingBar = ratingBar;
        this.rating = rating;
    }

    public void setRatingBar(RatingBar ratingBar) {
        this.ratingBar = ratingBar;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public RatingBar getRatingBar() {
        return this.ratingBar;
    }

    public float getRating() {
        return this.rating;
    }

    @Override
    public void onClick(View view) {
        if (this.ratingBar != null) {
            this.ratingBar.setRating(this.rating);
        }
    }

}
