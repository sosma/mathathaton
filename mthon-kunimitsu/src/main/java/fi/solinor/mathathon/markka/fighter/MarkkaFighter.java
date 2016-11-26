package fi.solinor.mathathon.markka.fighter;

import fi.solinor.mathathon.painimatsi.fighter.Fighter;
import fi.solinor.mathathon.painimatsi.utils.GameState;
import fi.solinor.mathathon.painimatsi.utils.Move;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class MarkkaFighter extends Fighter {

    HashMap<Move, Integer> loki;

    HashMap<Move, Integer> painot;
    
    int stamina;

    int rnd;
    
    int total;
   

    @Override
    public Move getMove() {
        Random random = new Random();
        painot.put(Move.NORMAALIHYOKKAYS, -4 * loki.get(Move.SUPERHYOKKAYS) + loki.get(Move.HEITTO) + loki.get(Move.PARANNUS));
        painot.put(Move.PUOLUSTUS, 3 * loki.get(Move.SUPERHYOKKAYS) + -2 * loki.get(Move.HEITTO) + -2 * loki.get(Move.PARANNUS));
        painot.put(Move.SUPERHYOKKAYS, 4 * loki.get(Move.NORMAALIHYOKKAYS) + -3 * loki.get(Move.PUOLUSTUS) + 5 * loki.get(Move.HEITTO) + 5 * loki.get(Move.PARANNUS));
        painot.put(Move.HEITTO, -1 * loki.get(Move.NORMAALIHYOKKAYS) + 2 * loki.get(Move.PUOLUSTUS) + -5 * loki.get(Move.SUPERHYOKKAYS));
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

       rnd = random.nextInt(painot.get(Move.PARANNUS) + 1);
        int superrnd = random.nextInt(100)*stamina;

        if (rnd <= painot.get(Move.NORMAALIHYOKKAYS)) {
            return Move.NORMAALIHYOKKAYS;
        } else if (rnd <= painot.get(Move.PUOLUSTUS)) {
            return Move.PUOLUSTUS;
        } else if (rnd <= painot.get(Move.SUPERHYOKKAYS)) {
            if (superrnd > 10000) {
            return Move.SUPERHYOKKAYS;            
            }
            else {
                return Move.HEITTO;            
            }
        } else if (rnd <= painot.get(Move.HEITTO)) {
            return Move.HEITTO;
        } else if (rnd <= painot.get(Move.PARANNUS)) {
            return Move.PARANNUS;
        } else {
            return Move.HEITTO;
        }

    }

    @Override
    public void readState(GameState gs) {
        stamina = gs.getMyStamina();
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


}