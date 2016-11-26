package fi.solinor.mathathon.markka.fighter;

import fi.solinor.mathathon.painimatsi.fighter.Fighter;
import fi.solinor.mathathon.painimatsi.utils.GameState;
import fi.solinor.mathathon.painimatsi.utils.Move;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NOTFighter extends Fighter {

    ArrayList<Move> edelliset = new ArrayList();
    HashMap<Move, Integer> loki;
    int i = 0;
    HashMap<Move, Integer> painot;

    int rnd;

    public Move counter(Move move) {
        Random random = new Random();
        int arvo = random.nextInt(2);
        if (move.equals(Move.NORMAALIHYOKKAYS)) {
            return Move.SUPERHYOKKAYS;
        }
        if (move.equals(Move.PUOLUSTUS)) {
            if (arvo == 0) {
                return Move.HEITTO;
            }
            return move.PARANNUS;
        }
        if (move.equals(Move.SUPERHYOKKAYS)) {
            return Move.PUOLUSTUS;
        }
        if (move.equals(Move.HEITTO)) {
            if (arvo == 0) {
                return Move.SUPERHYOKKAYS;
            }
            return Move.PARANNUS;

        }

        if (move.equals(Move.PARANNUS)) {
            if (arvo == 0) {
                return Move.SUPERHYOKKAYS;
            }
            return Move.NORMAALIHYOKKAYS;
        }
        return Move.NORMAALIHYOKKAYS;
    }

    @Override
    public Move getMove() {
        loki = kaanna();
        Random random = new Random();
        painot.put(Move.NORMAALIHYOKKAYS, 4 * loki.get(Move.SUPERHYOKKAYS) + -1 * loki.get(Move.HEITTO) + -1*loki.get(Move.PARANNUS));
        painot.put(Move.PUOLUSTUS, -3 * loki.get(Move.SUPERHYOKKAYS) + 2 * loki.get(Move.HEITTO) + 2 * loki.get(Move.PARANNUS));
        painot.put(Move.SUPERHYOKKAYS, -4 * loki.get(Move.NORMAALIHYOKKAYS) + 3 * loki.get(Move.PUOLUSTUS) + -5 * loki.get(Move.HEITTO) + -5 * loki.get(Move.PARANNUS));
        painot.put(Move.HEITTO, 1 * loki.get(Move.NORMAALIHYOKKAYS) + -2 * loki.get(Move.PUOLUSTUS) + 5 * loki.get(Move.SUPERHYOKKAYS));
        painot.put(Move.PARANNUS, loki.get(Move.HEITTO));

        for (Move move : painot.keySet()) {
            if (painot.get(move) < 0) {
                painot.put(move, 0);
            }
        }

        painot.put(Move.PUOLUSTUS, painot.get(Move.NORMAALIHYOKKAYS) + painot.get(Move.PUOLUSTUS));
        painot.put(Move.SUPERHYOKKAYS, painot.get(Move.PUOLUSTUS) + painot.get(Move.SUPERHYOKKAYS));
        painot.put(Move.HEITTO, painot.get(Move.SUPERHYOKKAYS) + painot.get(Move.HEITTO));
        painot.put(Move.PARANNUS, painot.get(Move.HEITTO) + painot.get(Move.PARANNUS));
        boolean noppa = random.nextBoolean();
        if (false) {
            rnd = random.nextInt(painot.get(Move.PARANNUS) + 1);
            if (rnd <= painot.get(Move.NORMAALIHYOKKAYS)) {
                return counter(Move.NORMAALIHYOKKAYS);
            } else if (rnd <= painot.get(Move.PUOLUSTUS)) {
                return counter(Move.PUOLUSTUS);
            } else if (rnd <= painot.get(Move.SUPERHYOKKAYS)) {
                return counter(Move.SUPERHYOKKAYS);
            } else if (rnd <= painot.get(Move.HEITTO)) {
                return (Move.HEITTO);
            } else if (rnd <= painot.get(Move.PARANNUS)) {
                return counter(Move.PARANNUS);
            } else {
                return counter(Move.SUPERHYOKKAYS);
            }
        } else {
            rnd = random.nextInt(painot.get(Move.PARANNUS) + 1);
            if (rnd <= painot.get(Move.NORMAALIHYOKKAYS)) {
                return (Move.NORMAALIHYOKKAYS);
            } else if (rnd <= painot.get(Move.PUOLUSTUS)) {
                return (Move.PUOLUSTUS);
            } else if (rnd <= painot.get(Move.SUPERHYOKKAYS)) {
                return (Move.SUPERHYOKKAYS);
            } else if (rnd <= painot.get(Move.HEITTO)) {
                return (Move.HEITTO);
            } else if (rnd <= painot.get(Move.PARANNUS)) {
                return (Move.PARANNUS);
            } else {
                return (Move.SUPERHYOKKAYS);
            }
        }

    }

    @Override
    public void readState(GameState gs
    ) {
        if (i < 200) {
            edelliset.add(gs.getEnemyMove());
        } else {
            edelliset.remove(0);
            edelliset.add(gs.getEnemyMove());
        }
        if (gs.getEnemyMove() != null) {
            loki.put(gs.getEnemyMove(), loki.get(gs.getEnemyMove()) + 1);
        }

    }

    @Override
    public void startGame() {

        loki = new HashMap();
        loki.put(Move.NORMAALIHYOKKAYS, 1);
        loki.put(Move.PUOLUSTUS, 1);
        loki.put(Move.SUPERHYOKKAYS, 1);
        loki.put(Move.HEITTO, 1);
        loki.put(Move.PARANNUS, 1);
        loki.put(Move.NULL, 0);

        painot = new HashMap();

        Random random = new Random();

    }

    public HashMap<Move, Integer> kaanna() {
        HashMap<Move, Integer> uusi = new HashMap();
        uusi.put(Move.NORMAALIHYOKKAYS, 1);
        uusi.put(Move.PUOLUSTUS, 1);
        uusi.put(Move.SUPERHYOKKAYS, 1);
        uusi.put(Move.HEITTO, 1);
        uusi.put(Move.PARANNUS, 1);
        uusi.put(Move.NULL, 0);

        for (Move move : edelliset) {
            uusi.put(move, loki.get(move) + 1);
        }
        return uusi;
    }

}
