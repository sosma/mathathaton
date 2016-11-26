package fi.solinor.mathathon.markka.fighter;

import fi.solinor.mathathon.painimatsi.fighter.Fighter;
import fi.solinor.mathathon.painimatsi.utils.GameState;
import fi.solinor.mathathon.painimatsi.utils.Move;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class testFighter extends Fighter {

    HashMap<Move, Integer> loki = new HashMap();
    HashMap<Move, Integer> painot = new HashMap();
    Random random = new Random();

    ArrayList<Integer> tulokset = new ArrayList();
    private int sarja = 0;
    private Random vaihdaSarjaa = new Random();
    private int vaihe = 0;
    HashMap<Move, ArrayList<Integer>> data = new HashMap();

    ArrayList<Move> vastustaja = new ArrayList();
    ArrayList<Move> pelaaja = new ArrayList();

    ArrayList<Move> siirrot = new ArrayList();

    int state = 0;
    int baitsize = 500;
    int turn = 0;

    int rnd;
    int total;
    int value = 0;

    boolean useAnalyysi = false;

    @Override
    public Move getMove() {
        if (!(tulokset.size() < 1000)) {
            value = summaaResult();

        }

        painot.put(Move.NORMAALIHYOKKAYS, -4 * loki.get(Move.SUPERHYOKKAYS) + loki.get(Move.HEITTO) + loki.get(Move.PARANNUS));
        painot.put(Move.PUOLUSTUS, 3 * loki.get(Move.SUPERHYOKKAYS) + -2 * loki.get(Move.HEITTO) + -2 * loki.get(Move.PARANNUS));
        painot.put(Move.SUPERHYOKKAYS, 4 * loki.get(Move.NORMAALIHYOKKAYS) + -3 * loki.get(Move.PUOLUSTUS) + 5 * loki.get(Move.HEITTO) + 5 * loki.get(Move.PARANNUS));
        painot.put(Move.HEITTO, -1 * loki.get(Move.NORMAALIHYOKKAYS) + 2 * loki.get(Move.PUOLUSTUS) + -5 * loki.get(Move.SUPERHYOKKAYS));
        painot.put(Move.PARANNUS, loki.get(Move.HEITTO));

        total = painot.get(Move.NORMAALIHYOKKAYS) + painot.get(Move.PUOLUSTUS) + painot.get(Move.SUPERHYOKKAYS) + painot.get(Move.HEITTO) + painot.get(Move.PARANNUS);

        painot.put(Move.NORMAALIHYOKKAYS, painot.get(Move.NORMAALIHYOKKAYS) + total / 5);
        painot.put(Move.PUOLUSTUS, painot.get(Move.PUOLUSTUS) + 2 * total / 5);
        painot.put(Move.SUPERHYOKKAYS, painot.get(Move.SUPERHYOKKAYS) - total / 15);
        painot.put(Move.HEITTO, painot.get(Move.HEITTO) + 2 * total / 5);
        painot.put(Move.PARANNUS, painot.get(Move.PARANNUS) + 2 * total / 5);

        for (Move move : painot.keySet()) {
            if (painot.get(move) < 0) {
                painot.put(move, 0);
            }
        }

        painot.put(Move.PUOLUSTUS, painot.get(Move.NORMAALIHYOKKAYS) + painot.get(Move.PUOLUSTUS));
        painot.put(Move.SUPERHYOKKAYS, painot.get(Move.PUOLUSTUS) + painot.get(Move.SUPERHYOKKAYS));
        painot.put(Move.HEITTO, painot.get(Move.SUPERHYOKKAYS) + painot.get(Move.HEITTO));
        painot.put(Move.PARANNUS, painot.get(Move.HEITTO) + painot.get(Move.PARANNUS));

        rnd = random.nextInt(painot.get(Move.PARANNUS) + 1);
        if (value < 0) {
            if (rnd <= painot.get(Move.NORMAALIHYOKKAYS)) {
                return analyysi(Move.NORMAALIHYOKKAYS);
            } else if (rnd <= painot.get(Move.PUOLUSTUS)) {
                return analyysi(Move.PUOLUSTUS);
            } else if (rnd <= painot.get(Move.SUPERHYOKKAYS)) {
                return analyysi(Move.SUPERHYOKKAYS);
            } else if (rnd <= painot.get(Move.HEITTO)) {
                return analyysi(Move.HEITTO);
            } else if (rnd <= painot.get(Move.PARANNUS)) {
                return analyysi(Move.PARANNUS);
            } else {
                return analyysi(Move.SUPERHYOKKAYS);
            }
        }
        if (rnd <= painot.get(Move.NORMAALIHYOKKAYS)) {
            return Move.NORMAALIHYOKKAYS;
        } else if (rnd <= painot.get(Move.PUOLUSTUS)) {
            return Move.PUOLUSTUS;
        } else if (rnd <= painot.get(Move.SUPERHYOKKAYS)) {
            return Move.SUPERHYOKKAYS;
        } else if (rnd <= painot.get(Move.HEITTO)) {
            return Move.HEITTO;
        } else if (rnd <= painot.get(Move.PARANNUS)) {
            return Move.PARANNUS;
        } else {
            return Move.SUPERHYOKKAYS;
        }

    }

    public int summaaResult() {
        int i = 0;
        tulokset = new ArrayList();
        for (Integer integer : tulokset) {
            i += integer;
        }
        if (i < -10) {
            useAnalyysi = !useAnalyysi;
        }
        return -1;
    }

    public int result(Move oma, Move vihu) {
        switch (oma) {
            case HEITTO:
                switch (vihu) {
                    case HEITTO:
                        return 0;
                    case NORMAALIHYOKKAYS:
                        return -2;
                    case SUPERHYOKKAYS:
                        return -5;
                    case PUOLUSTUS:
                        return 2;
                    case PARANNUS:
                        return -2;
                    case NULL:
                        return 0;
                }
            case NORMAALIHYOKKAYS:
                switch (vihu) {
                    case HEITTO:
                        return 2;
                    case NORMAALIHYOKKAYS:
                        return 0;
                    case SUPERHYOKKAYS:
                        return -3;
                    case PUOLUSTUS:
                        return 0;
                    case PARANNUS:
                        return 2;
                    case NULL:
                        return 2;
                }
            case SUPERHYOKKAYS:
                switch (vihu) {
                    case HEITTO:
                        return 5;
                    case NORMAALIHYOKKAYS:
                        return 3;
                    case SUPERHYOKKAYS:
                        return 0;
                    case PUOLUSTUS:
                        return -5;
                    case PARANNUS:
                        return 5;
                    case NULL:
                        return 5;
                }
            case PUOLUSTUS:
                switch (vihu) {
                    case HEITTO:
                        return -2;
                    case NORMAALIHYOKKAYS:
                        return 0;
                    case SUPERHYOKKAYS:
                        return 5;
                    case PUOLUSTUS:
                        return 0;
                    case PARANNUS:
                        return -2;
                    case NULL:
                        return 0;
                }
            case PARANNUS:
                switch (vihu) {
                    case HEITTO:
                        return 2;
                    case NORMAALIHYOKKAYS:
                        return -2;
                    case SUPERHYOKKAYS:
                        return -5;
                    case PUOLUSTUS:
                        return 2;
                    case PARANNUS:
                        return 0;
                    case NULL:
                        return 2;
                }
            case NULL:
                switch (vihu) {
                    case HEITTO:
                        return 0;
                    case NORMAALIHYOKKAYS:
                        return -2;
                    case SUPERHYOKKAYS:
                        return -5;
                    case PUOLUSTUS:
                        return 0;
                    case PARANNUS:
                        return 2;
                    case NULL:
                        return 0;
                }

        }
        return 0;
    }

    @Override
    public void readState(GameState gs) {

        if (tulokset.size() < 1000) {
            tulokset.add(result(gs.getMyMove(), gs.getEnemyMove()));
        } else {
            tulokset.add(result(gs.getMyMove(), gs.getEnemyMove()));
            tulokset.remove(0);
        }
        turn++;

        pelaaja.add(gs.getMyMove());
        vastustaja.add(gs.getEnemyMove());

    }

    @Override
    public void startGame() {
        loki.put(Move.NORMAALIHYOKKAYS, 1);
        loki.put(Move.PUOLUSTUS, 1);
        loki.put(Move.SUPERHYOKKAYS, 1);
        loki.put(Move.HEITTO, 1);
        loki.put(Move.PARANNUS, 1);
        loki.put(Move.NULL, 0);
        siirrot.add(Move.NORMAALIHYOKKAYS);
        siirrot.add(Move.SUPERHYOKKAYS);
        siirrot.add(Move.PUOLUSTUS);
        siirrot.add(Move.PARANNUS);
        siirrot.add(Move.HEITTO);
        Collections.shuffle(siirrot);

    }

    public Move bait() {
        if (turn < (baitsize - 4 / 5 * baitsize)) {
            return siirrot.get(0);
        } else if (turn < (baitsize - 3 / 5 * baitsize)) {
            return siirrot.get(1);
        } else if (turn < (baitsize - 2 / 5 * baitsize)) {
            return siirrot.get(2);
        } else if (turn < (baitsize - 1 / 5 * baitsize)) {
            return siirrot.get(3);

        } else {
            return siirrot.get(4);

        }
    }

    public Move counter(Move in) {
        switch (in) {
            case HEITTO:
                return Move.PARANNUS;
            case NORMAALIHYOKKAYS:
                return Move.PUOLUSTUS;
            case NULL:
                return Move.SUPERHYOKKAYS;
            case PARANNUS:
                return Move.NORMAALIHYOKKAYS;
            case SUPERHYOKKAYS:
                return Move.HEITTO;
            default:
                return Move.PUOLUSTUS;
        }
    }
    //HEITTO, NORMAALIHYOKKAYS, NULL, PARANNUS, PUOLUSTUS, SUPERHYOKKAYS

    public Move analyysi(Move move) {
        data = toHashMap();
        ArrayList<Integer> statistiikka = data.get(move);
        int todennakoisin = 0;
        int monesko = 0;
        for (int i = 0; i < statistiikka.size(); i++) {
            if (statistiikka.get(i) > todennakoisin) {
                monesko = i;
            }
            switch (monesko) {
                case 0:
                    return counter(counter(Move.HEITTO));
                case 1:
                    return counter(counter(Move.NORMAALIHYOKKAYS));
                case 2:
                    return counter(counter(Move.NULL));
                case 3:
                    return counter(counter(Move.PARANNUS));
                case 4:
                    return counter(counter(Move.PUOLUSTUS));
                case 5:
                    return counter(counter(Move.SUPERHYOKKAYS));

            }

        }

        return Move.NORMAALIHYOKKAYS;
    }

    public ArrayList<Integer> count(ArrayList<Move> pelaaja, ArrayList<Move> vastustaja, Move avain) {
        ArrayList<Integer> data = new ArrayList();

        for (int i = 0; i < pelaaja.size(); i++) {
            if (pelaaja.get(i).equals(avain)) {
                switch (vastustaja.get(i)) {
                    case HEITTO:
                        data.set(0, data.get(0) + 1);
                        break;
                    case NORMAALIHYOKKAYS:
                        data.set(1, data.get(1) + 1);
                        break;
                    case NULL:
                        data.set(2, data.get(2) + 1);
                        break;
                    case PARANNUS:
                        data.set(3, data.get(3) + 1);
                        break;
                    case PUOLUSTUS:
                        data.set(4, data.get(4) + 1);
                        break;
                    default:
                        data.set(5, data.get(5) + 1);
                        break;
                }
            }
        }

        return data;
    }

    //HEITTO, NORMAALIHYOKKAYS, NULL, PARANNUS, PUOLUSTUS, SUPERHYOKKAYS
    public HashMap<Move, ArrayList<Integer>> toHashMap() {
        HashMap<Move, ArrayList<Integer>> jakauma = new HashMap();
        ArrayList<Integer> alustus = new ArrayList();
        alustus.add(0);
        alustus.add(0);
        alustus.add(0);
        alustus.add(0);
        alustus.add(0);
        alustus.add(0);
        jakauma.put(siirrot.get(0), alustus);
        jakauma.put(siirrot.get(1), alustus);
        jakauma.put(siirrot.get(2), alustus);
        jakauma.put(siirrot.get(3), alustus);
        jakauma.put(siirrot.get(4), alustus);
        jakauma.put(Move.NULL, alustus);
        for (Move move : siirrot) {
            jakauma.put(move, count(pelaaja, vastustaja, move));
        }
        return jakauma;
    }
}
