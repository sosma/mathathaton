package fi.solinor.mathathon.markka.fighter;

import fi.solinor.mathathon.painimatsi.fighter.Fighter;
import fi.solinor.mathathon.painimatsi.utils.GameState;
import fi.solinor.mathathon.painimatsi.utils.Move;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BaitFighter extends Fighter {

    private int sarja = 0;
    private Random vaihdaSarjaa = new Random();
    private int vaihe = 0;

    ArrayList<Move> vastustaja = new ArrayList();
    ArrayList<Move> pelaaja = new ArrayList();

    ArrayList<Move> siirrot = new ArrayList();

    int state = 0;
    int baitsize = 500;
    int turn = 0;

    @Override
    public Move getMove() {
        if (turn < baitsize) {
            return bait();
        }
        if (vaihdaSarjaa.nextFloat() > 0.95) {
            sarja++;
            if (sarja == 3) {
                sarja = 0;
            }
        }
        switch(sarja){
            case 0:
                return sarja0(vaihe);
            case 1:
                return sarja1(vaihe);
            case 2:
                return sarja2(vaihe);
        }
        return analyysi();
    }

    @Override
    public void readState(GameState gs) {
        turn++;
        pelaaja.add(gs.getMyMove());
        vastustaja.add(gs.getEnemyMove());
    }

    @Override
    public void startGame() {
        siirrot.add(Move.NORMAALIHYOKKAYS);
        siirrot.add(Move.SUPERHYOKKAYS);
        siirrot.add(Move.PUOLUSTUS);
        siirrot.add(Move.PARANNUS);
        siirrot.add(Move.HEITTO);
        Collections.shuffle(siirrot);

    }

    public Move pelaaSarja() {
        if (state < 10) {
            state++;
        } else {
            state = 0;
        }
        return Move.NULL;
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

    public Move analyysi() {
        return Move.NULL;
    }

    private Move sarja0(int vaihe) {
        switch (vaihe) {
            case 0:
                return Move.NORMAALIHYOKKAYS;
            case 1:
                return Move.HEITTO;
            case 2:
                return Move.NORMAALIHYOKKAYS;
            case 3:
                return Move.HEITTO;
            case 4:
                return Move.PARANNUS;
            case 5:
                return Move.SUPERHYOKKAYS;
            case 6:
                return Move.SUPERHYOKKAYS;

        }
        return Move.PUOLUSTUS;
    }

    private Move sarja1(int vaihe) {
        switch (vaihe) {
            case 0:
                return Move.HEITTO;
            case 1:
                return Move.HEITTO;
            case 2:
                return Move.SUPERHYOKKAYS;
            case 3:
                return Move.HEITTO;
            case 4:
                return Move.PARANNUS;
            case 5:
                return Move.PARANNUS;
            case 6:
                return Move.SUPERHYOKKAYS;

        }
        return Move.PUOLUSTUS;
    }

    private Move sarja2(int vaihe) {
        switch (vaihe) {
            case 0:
                return Move.SUPERHYOKKAYS;
            case 1:
                return Move.HEITTO;
            case 2:
                return Move.NORMAALIHYOKKAYS;
            case 3:
                return Move.HEITTO;
            case 4:
                return Move.SUPERHYOKKAYS;
            case 5:
                return Move.HEITTO;
            case 6:
                return Move.NORMAALIHYOKKAYS;

        }
        return Move.PUOLUSTUS;
    }

}