import java.util.*;

/**
 * Class {@code Dote} represents a dot on the cartesian coordinate system.
 */
public class Dot {
    private int x;
    private int y;
    private double angle;

    public Dot() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Representation of coordinates of a dote
     *
     * @return "(x,y)"
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * The method {@code dotesGenerator} creates an array of objects of
     * the class {@code Dot} and fills each elements of the one
     * with random integer numbers.
     *
     * @param count size of array
     * @return an array with random integer numbers
     */
    public static Dot[] dotesGenerator(int count) {
        Random rand = new Random();
        Dot[] dotes = new Dot[count];
        for (int i = 0; i < dotes.length; i++)
            dotes[i] = new Dot();

        for (int i = 0; i < count; i++) {
            dotes[i].setX(rand.nextInt(15));
            dotes[i].setY(rand.nextInt(15));
        }
        return dotes;
    }

    /**
     * Offset of all dotes relatively the center dote.
     * Firstly, we find the center of all dotes using these formulas:
     * Xcenter = (Xmax + Xmin) / 2
     * Ycenter = (Ymax + Ymin) / 2
     * where Xmax and Xmin are maximum and minimum of all dotes by X
     * and for Ymax and Ymin are maximum and minimum of all dotes by Y
     * Secondly, displace all dotes subtracting the coordinates of ones
     * by Xcenter and Ycenter
     *
     * @param dotes an array of Dot objects
     * @return a displaced array of Dot objects
     */
    public static Dot[] offsetToCenter(Dot[] dotes) {
        int dotesLen = dotes.length;
        Integer[] buff = new Integer[dotesLen];

        for (int i = 0; i < dotesLen; i++)
            buff[i] = dotes[i].getX();
        List<Integer> listX = Arrays.asList(buff);

        for (int i = 0; i < dotesLen; i++)
            buff[i] = dotes[i].getY();
        List<Integer> listY = Arrays.asList(buff);

        int xCenter = (Collections.max(listX) + Collections.min(listX)) / 2;
        int yCenter = (Collections.max(listY) + Collections.min(listY)) / 2;

        /* Subtracting all coordinates of dotes by center-dot.
         * Thus, after it center will be in the (0,0).
         */
        for (Dot d : dotes) {
            d.setX(d.getX() - xCenter);
            d.setY(d.getY() - yCenter);
        }
        return dotes;
    }

    /**
     * Sorting all dotes by angle.
     * For finding angle from coordinates of dote and axis of X or Y
     * it was used trigonometric inverse function - arc tangent
     *
     * @param dotes an array of Dot objects
     * @return list of Dot objects those sorted by theirs angles
     */
    public static List<Dot> sortByAngle(Dot[] dotes) {
        double x, y;
        for (Dot d : dotes) {
            x = d.getX();
            y = d.getY();
            if ((x < 0) && (y < 0))
                d.setAngle(Math.atan(Math.abs(x) / Math.abs(y)));
            else if ((x < 0) && (y > 0))
                d.setAngle(Math.atan(Math.abs(y) / Math.abs(x)) + 1.570796326);
            else if ((x > 0) && (y > 0))
                d.setAngle(Math.atan(Math.abs(x) / Math.abs(y)) + 3.1415926536);
            else if ((x > 0) && (y < 0))
                d.setAngle(Math.atan(Math.abs(y) / Math.abs(x)) + 4.7123889804);
            else {
                if ((x == 0) && (y == 0))
                    d.setAngle(0);
                else if (y != 0)
                    d.setAngle((y > 0) ? 3.1415926536 : 6.2831853064);
                else if (x != 0)
                    d.setAngle((x > 0) ? 4.7123889804 : 1.570796326);
            }
        }

        /**
         * Converting the array of dotes to the list, it's
         * need to using static method {@code sort} of class
         * the {@code Collections} for sorting elements of the list
         * by angle.
         * And in this {@code sort} method we override the method compare
         * of the {@code Comparable} class for sorting exactly by angle.
         */
        List<Dot> dotesList = Arrays.asList(dotes);
        Collections.sort(dotesList, (o1, o2) -> {
            if (o1.getAngle() < o2.getAngle())
                return -1;
            if (o1.getAngle() > o2.getAngle())
                return 1;
            return 0;
        });
        return dotesList;
    }

    /**
     * In this method we use API of the website webmath.ru.
     * Firstly, creating the {@code StringBuilder } object {@code link} for storing
     * the URI of figure of closed polygonal chain
     * Secondly, add URL-pattern for each dot of the array of dotes.
     * Thirdly, add the URL-pattern of first dote, it's needed to close
     * a polygonal chain.
     * Building the figure on the site works if the count of dotes less then 175!
     *
     * @param dotes an array of Dot objects
     */
    public static void buildPolygonalChain(Dot[] dotes) {
        StringBuilder link = new StringBuilder();
        link.append("http://www.webmath.ru/web/prog30_2.php?");

        StringBuilder doteKoef;
        for (int i = 0; i < dotes.length; i++) {
            // X coordinate
            doteKoef = new StringBuilder();
            doteKoef.append("koef%5B");
            doteKoef.append(i + 1);
            doteKoef.append("%5D%5B1%5D=");
            doteKoef.append(dotes[i].getX());
            doteKoef.append("&");
            link.append(doteKoef);

            // Y coordinate
            doteKoef = new StringBuilder();
            doteKoef.append("koef%5B");
            doteKoef.append(i + 1);
            doteKoef.append("%5D%5B2%5D=");
            doteKoef.append(dotes[i].getY());
            doteKoef.append("&");
            link.append(doteKoef);
        }
        int countDotes = dotes.length + 1;
        link.append("koef%5B" + countDotes + "%5D%5B1%5D=" + dotes[0].getX() + "&" +
                    "koef%5B" + countDotes + "%5D%5B2%5D=" + dotes[0].getY() + "&");
        link.append("koordinat=1&oss=1&chislo_tochek=");
        link.append(dotes.length + 1);
        System.out.println(link.toString());
    }
}
