package utils;

import actor.Actor;
import entertainment.Show;
import user.User;

import java.util.Comparator;

/**
 * Class which implements the comparators used for sorting arraylists within the program
 */
public final class Comparators {
    private Comparators() {
    }

    /**
     * Sort the users list by given ratings and by name in ascending order
     */
    public static class SortUserByRatingAsc implements Comparator<User> {
        /**
         * Override the compare function
         * @param o1 the first user
         * @param o2 the second user
         * @return the sorting order
         */
        @Override
        public int compare(final User o1, final User o2) {
            if (o2.getRatedMovies().size() == o1.getRatedMovies().size()) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
            if (o2.getRatedMovies().size() < o1.getRatedMovies().size()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort the users list by given ratings and by name in descending order
     */
    public static class SortUserByRatingDesc implements Comparator<User> {
        /**
         * Override the compare function
         * @param o1 the first user
         * @param o2 the second user
         * @return the sorting order
         */
        @Override
        public int compare(final User o1, final User o2) {
            if (o1.getRatedMovies().size() == o2.getRatedMovies().size()) {
                return o2.getUsername().compareTo(o1.getUsername());
            }
            if (o1.getRatedMovies().size() < o2.getRatedMovies().size()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by rating and by name in ascending order
     */
    public static class SortVideoByRatingNameAsc implements Comparator<Show> {
        /**
         * Override the compare function
         * @param o1 the first show
         * @param o2 the second show
         * @return the sorting order
         */
        @Override
        public int compare(final Show o1, final Show o2) {
            if (o2.getAverageRating() == o1.getAverageRating()) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            if (o2.getAverageRating() < o1.getAverageRating()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by rating and by name in descending order
     */
    public static class SortVideoByRatingNameDesc implements Comparator<Show> {
        /**
         * Override the compare function
         * @param o1 the first show
         * @param o2 the second show
         * @return the sorting order
         */
        @Override
        public int compare(final Show o1, final Show o2) {
            if (o1.getAverageRating() == o2.getAverageRating()) {
                return o2.getTitle().compareTo(o1.getTitle());
            }
            if (o1.getAverageRating() < o2.getAverageRating()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by favorite and by name in ascending order
     */
    public static class SortVideoByFavoriteNameAsc implements Comparator<Show> {
        /**
         * Override the compare function
         * @param o1 the first show
         * @param o2 the second show
         * @return the sorting order
         */
        @Override
        public int compare(final Show o1, final Show o2) {
            if (o2.getFavoriteNumber() == o1.getFavoriteNumber()) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            if (o2.getFavoriteNumber() < o1.getFavoriteNumber()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by favorite and by name in descending order
     */
    public static class SortVideoByFavoriteNameDesc implements Comparator<Show> {
        /**
         * Override the compare function
         * @param o1 the first show
         * @param o2 the second show
         * @return the sorting order
         */
        @Override
        public int compare(final Show o1, final Show o2) {
            if (o1.getFavoriteNumber() == o2.getFavoriteNumber()) {
                return o2.getTitle().compareTo(o1.getTitle());
            }
            if (o1.getFavoriteNumber() < o2.getFavoriteNumber()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by duration and by name in ascending order
     */
    public static class SortVideoByDurationNameAsc implements Comparator<Show> {
        /**
         * Override the compare function
         * @param o1 the first show
         * @param o2 the second show
         * @return the sorting order
         */
        @Override
        public int compare(final Show o1, final Show o2) {
            if (o2.getDuration() == o1.getDuration()) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            if (o2.getDuration() < o1.getDuration()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by view and by name in descending order
     */
    public static class SortVideoByDurationNameDesc implements Comparator<Show> {
        /**
         * Override the compare function
         * @param o1 the first show
         * @param o2 the second show
         * @return the sorting order
         */
        @Override
        public int compare(final Show o1, final Show o2) {
            if (o1.getDuration() == o2.getDuration()) {
                return o2.getTitle().compareTo(o1.getTitle());
            }
            if (o1.getDuration() < o2.getDuration()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by view and by name in ascending order
     */
    public static class SortVideoByViewsNameAsc implements Comparator<Show> {
        /**
         * Override the compare function
         * @param o1 the first show
         * @param o2 the second show
         * @return the sorting order
         */
        @Override
        public int compare(final Show o1, final Show o2) {
            if (o2.getViewNumber() == o1.getViewNumber()) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            if (o2.getViewNumber() < o1.getViewNumber()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort video list by view and by name in descending order
     */
    public static class SortVideoByViewsNameDesc implements Comparator<Show> {
        /**
         * Override the compare function
         * @param o1 the first show
         * @param o2 the second show
         * @return the sorting order
         */
        @Override
        public int compare(final Show o1, final Show o2) {
            if (o1.getViewNumber() == o2.getViewNumber()) {
                return o2.getTitle().compareTo(o1.getTitle());
            }
            if (o1.getViewNumber() < o2.getViewNumber()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort actors list by average rating and by name in ascending order
     */
    public static class SortActorByAverageAsc implements Comparator<Actor> {
        /**
         * Override the compare function
         * @param o1 the first actor
         * @param o2 the second actor
         * @return the sorting order
         */
        @Override
        public int compare(final Actor o1, final Actor o2) {
            if (o2.getRating() == o1.getRating()) {
                return o1.getName().compareTo(o2.getName());
            }
            if (o2.getRating() < o1.getRating()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort actors list by average rating and by name in descending order
     */
    public static class SortActorByAverageDesc implements Comparator<Actor> {
        /**
         * Override the compare function
         * @param o1 the first actor
         * @param o2 the second actor
         * @return the sorting order
         */
        @Override
        public int compare(final Actor o1, final Actor o2) {
            if (o1.getRating() == o2.getRating()) {
                return o2.getName().compareTo(o1.getName());
            }
            if (o1.getRating() < o2.getRating()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort actors list by the number of awards and by name in ascending order
     */
    public static class SortActorByAwardAsc implements Comparator<Actor> {
        /**
         * Override the compare function
         * @param o1 the first actor
         * @param o2 the second actor
         * @return the sorting order
         */
        @Override
        public int compare(final Actor o1, final Actor o2) {
            if (o2.getNumberOfAwards() == o1.getNumberOfAwards()) {
                return o1.getName().compareTo(o2.getName());
            }
            if (o2.getNumberOfAwards() < o1.getNumberOfAwards()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort actors list by the number of awards and by name in descending order
     */
    public static class SortActorByAwardDesc implements Comparator<Actor> {
        /**
         * Override the compare function
         * @param o1 the first actor
         * @param o2 the second actor
         * @return the sorting order
         */
        @Override
        public int compare(final Actor o1, final Actor o2) {
            if (o1.getNumberOfAwards() == o2.getNumberOfAwards()) {
                return o2.getName().compareTo(o1.getName());
            }
            if (o1.getNumberOfAwards() < o2.getNumberOfAwards()) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * Sort actors list by name in ascending order
     */
    public static class SortActorByNameAsc implements Comparator<Actor> {
        /**
         * Override the compare function
         * @param o1 the first actor
         * @param o2 the second actor
         * @return the sorting order
         */
        @Override
        public int compare(final Actor o1, final Actor o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    /**
     * Sort actors list by name in descending order
     */
    public static class SortActorByNameDesc implements Comparator<Actor> {
        /**
         * Override the compare function
         * @param o1 the first actor
         * @param o2 the second actor
         * @return the sorting order
         */
        @Override
        public int compare(final Actor o1, final Actor o2) {
            return o2.getName().compareTo(o1.getName());
        }
    }

    /**
     * Sort video list by rating in descending order
     */
    public static class SortVideoByRatingDesc implements Comparator<Show> {
        /**
         * Override the compare function
         * @param o1 the first show
         * @param o2 the second show
         * @return the sorting order
         */
        @Override
        public int compare(final Show o1, final Show o2) {
            return Double.compare(o2.getAverageRating(),
                    o1.getAverageRating());
        }
    }

    /**
     * Sort video list by favorite in descending order
     */
    public static class SortVideoByFavoriteDesc implements Comparator<Show> {
        /**
         * Override the compare function
         * @param o1 the first show
         * @param o2 the second show
         * @return the sorting order
         */
        @Override
        public int compare(final Show o1, final Show o2) {
            return Integer.compare(o2.getFavoriteNumber(),
                    o1.getFavoriteNumber());
        }
    }
}
