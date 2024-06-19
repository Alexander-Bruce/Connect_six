package tools;

public interface Analyse_Point {
    int EMPTY = 2;
    int BOARD_SIZE = 17;

    default void analyse_point(int []array, int side, int [] result){
        //1&&2：六连，长连
        analyse_six(array, side, result);
        //3：活5
        analyse_five(array, side, result);
        //4：眠5②：__OO__OOO__
        analyse_five_sleep(array, side, result);
        //5：死5
        analyse_five_dead(array, side, result);
        //6：活4
        analyse_four(array, side, result);
        //7：眠4：__OO____OO__类似
        analyse_four_sleep(array, side, result);
        //8：死4
        analyse_four_dead(array, side, result);
        //9：活3
        analyse_three(array, side, result);
        //10：朦胧3：__O__O__O__     __OO____O__
        analyse_three_vague(array, side, result);
        //11：眠3：__O__OO__
        analyse_three_sleep(array, side, result);
        //12：死3
        analyse_three_dead(array, side, result);
        //13：活2：__OO__
        analyse_two(array, side, result);
        //14：眠2：__O__O__
        analyse_two_sleep(array, side, result);
        //15：死2
        analyse_two_dead(array, side, result);
    }

    //1&&2：六连，长连
    default void analyse_six(int []array, int side, int [] result){
        int judge_continuous = 0;//用于判断多少个同类棋子连续
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            if (array[i] == side)
            {
                judge_continuous += 1;
                if (judge_continuous == 6)
                {
                    result[0] += 1;
                    for (int j = 0; j < 6; j++) array[i - j] = 1 - side;
                }
                if (judge_continuous > 6 && ((i < BOARD_SIZE - 1 && array[i + 1] != side) || i == BOARD_SIZE - 1))
                {
                    result[1] += 1;
                    for (int j = 0; j < judge_continuous; j++)
                    {
                        if (array[i - j] == side)
                            array[i - j] = -1;
                    }
                }
            }
            else
                judge_continuous = 0;
        }
    }

    //3：活5
    default void analyse_five(int []array, int side, int [] result){
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            if (i > 5 && judge_continuous == 5)
            {
                if (array[i] == EMPTY && array[i - 6] == EMPTY)//两头为空
                {
                    result[2] += 1;
                    for (int j = 1; j < 6; j++)
                    {
                        if (array[i - j] == side)
                            array[i - j] = -1;
                    }
                }
            }
            if (array[i] == side)
                judge_continuous += 1;
            else
                judge_continuous = 0;
        }
    }

    //4：眠5②：__OO__OOO__
    default void analyse_five_sleep(int []array, int side, int [] result){
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++){
            int recover_i = i;
            int judgeempty = 0;
            boolean flag = true;
            if (array[i] == side || array[i] == EMPTY)
            {
                if (array[i] == EMPTY) judgeempty += 1;
                i += 1;
                judge_continuous += 1;
                if (i > 12) break;
                int tempi = i + 5;
                while(i < tempi && flag) {
                    if (array[i] == side || array[i] == EMPTY) {
                        judge_continuous += 1;
                        if (array[i] == EMPTY) judgeempty += 1;
                    }
                    else if (array[i] == -1) {
                        while (i < BOARD_SIZE - 1 && array[i + 1] != -1) i += 1;
                        judge_continuous = 0;
                        flag = false;
                    } else {
                        i = recover_i;
                        break;
                    }
                    i++;
                }
            }
            if (judge_continuous == 6 && judgeempty == 1 && flag)
            {
                result[3] += 1;
                for (int j = 1; j < 7; j++){
                    if (array[i - j] == side) array[i - j] = -1;
                }
            } else if(flag){
                judge_continuous = 0;
                i = recover_i;
            }
        }
    }

    //5：死5
    default void analyse_five_dead(int []array, int side, int [] result){
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            int recover_i = i;
            boolean flag = true;
            if (array[i] == 1 - side)
            {
                i += 1;
                if (i > 12) break;
                int tempi = i + 5;
                while(i < tempi && flag)
                {
                    if (array[i] == side || array[i] == EMPTY) judge_continuous += 1;
                    else if (array[i] == -1) {
                        while (i < BOARD_SIZE - 1 && array[i + 1] != -1) i += 1;
                        judge_continuous = 0;
                        flag = false;
                    }
                    else {
                        i = recover_i;
                        break;
                    }
                    i++;
                }
            }
            if (i < BOARD_SIZE && judge_continuous == 5 && array[i] == 1 - side && flag)
            {
                result[4] += 1;
                for (int j = 1; j < 6; j++)
                {
                    if (array[i - j] == side)
                        array[i - j] = -1;
                }
            } else if(flag) {
                judge_continuous = 0;
                i = recover_i;
            }
        }
    }

    //6：活4
    default void analyse_four(int []array, int side, int [] result){
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            if (i > 4 && judge_continuous == 4)
            {
                if (array[i] == EMPTY && array[i - 5] == EMPTY)//两头为空
                {
                    result[5] += 1;
                    for (int j = 1; j < 5; j++)
                    {
                        if (array[i - j] == side)
                            array[i - j] = -1;
                    }
                }
            }
            if (array[i] == side)
                judge_continuous += 1;
            else
                judge_continuous = 0;
        }
    }

    //gpt generate
    //7：眠4②：__OO__OO__
    default void analyse_four_sleep(int []array, int side, int [] result){
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++){
            int recover_i = i;
            int judgeempty = 0;
            boolean flag = true;
            if (array[i] == side || array[i] == EMPTY)
            {
                if (array[i] == EMPTY) judgeempty += 1;
                i += 1;
                judge_continuous += 1;
                if (i > 12) break;
                int tempi = i + 4;
                while(i < tempi && flag) {
                    if (array[i] == side || array[i] == EMPTY) {
                        judge_continuous += 1;
                        if (array[i] == EMPTY) judgeempty += 1;
                    }
                    else if (array[i] == -1) {
                        while (i < BOARD_SIZE - 1 && array[i + 1] != -1) i += 1;
                        judge_continuous = 0;
                        flag = false;
                    } else {
                        i = recover_i;
                        break;
                    }
                    i++;
                }
            }
            if (judge_continuous == 5 && judgeempty == 1 && flag)
            {
                result[6] += 1;
                for (int j = 1; j < 6; j++){
                    if (array[i - j] == side) array[i - j] = -1;
                }
            } else if(flag){
                judge_continuous = 0;
                i = recover_i;
            }
        }
    }

    //8：死4
    default void analyse_four_dead(int []array, int side, int [] result){
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            int recover_i = i;
            boolean flag = true;
            if (array[i] == 1 - side)
            {
                i += 1;
                if (i > 12) break;
                int tempi = i + 4;
                while(i < tempi && flag)
                {
                    if (array[i] == side || array[i] == EMPTY) judge_continuous += 1;
                    else if (array[i] == -1) {
                        while (i < BOARD_SIZE - 1 && array[i + 1] != -1) i += 1;
                        judge_continuous = 0;
                        flag = false;
                    }
                    else
                    {
                        i = recover_i;
                        break;
                    }
                    i++;
                }
            }
            if (i < BOARD_SIZE && judge_continuous == 4 && array[i] == 1 - side && flag)
            {
                result[7] += 1;
                for (int j = 1; j < 5; j++)
                {
                    if (array[i - j] == side)
                        array[i - j] = -1;
                }
            } else if(flag) {
                judge_continuous = 0;
                i = recover_i;
            }
        }
    }

    //9：活3
    default void analyse_three(int []array, int side, int [] result){
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            if (i > 3 && judge_continuous == 3)
            {
                if (array[i] == EMPTY && array[i - 4] == EMPTY)//两头为空
                {
                    result[8] += 1;
                    for (int j = 1; j < 4; j++)
                    {
                        if (array[i - j] == side)
                            array[i - j] = -1;
                    }
                }
            }
            if (array[i] == side)
                judge_continuous += 1;
            else
                judge_continuous = 0;
        }
    }

    //10：朦胧3：__O__O__O__     __OO____O__
    default void analyse_three_vague(int []array, int side, int [] result){
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            int recover_i = i;
            int judgeempty = 0;
            boolean flag = true;
            if (array[i] == EMPTY)
            {
                i += 1;
                if (i > 10) break;
                int tempi = i + 5;
                while (i < tempi && flag)
                {
                    if (array[i] == side || array[i] == EMPTY)
                    {
                        judge_continuous += 1;
                        if (array[i] == EMPTY)
                            judgeempty += 1;
                    }
                    else if (array[i] == -1)
                    {
                        while (i < BOARD_SIZE - 1 && array[i + 1] != -1) i += 1;
                        judge_continuous = 0;
                        flag = false;
                    }
                    else
                    {
                        i = recover_i;
                        break;
                    }
                    i++;
                }
            }
            if (judge_continuous == 5 && judgeempty == 2 && array[i] == EMPTY && flag)
            {
                result[9] += 1;
                for (int j = 1; j < 6; j++)
                {
                    if (array[i - j] == side) array[i - j] = -1;
                }
            }
            else if(flag){
                judge_continuous = 0;
                i = recover_i;
            }
        }
    }

    //11：眠3：__O__OO__
    default void analyse_three_sleep(int []array, int side, int [] result){
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            int recover_i = i;
            int judgeempty = 0;
            boolean flag = true;
            if (array[i] == side || array[i] == EMPTY)
            {
                if (array[i] == EMPTY) judgeempty += 1;
                i += 1;
                judge_continuous += 1;
                if (i > 12) break;
                int tempi = i + 5;
                while (i < tempi && flag)
                {
                    if (array[i] == side || array[i] == EMPTY) {
                        judge_continuous += 1;
                        if (array[i] == EMPTY)
                            judgeempty += 1;
                    }
                    else if (array[i] == -1) {
                        while (i < BOARD_SIZE - 1 && array[i + 1] != -1)
                            i += 1;
                        judge_continuous = 0;
                        flag = false;
                    }
                    else {
                        i = recover_i;
                        break;
                    }
                    i++;
                }
            }
            if (judge_continuous == 6 && judgeempty == 3 && flag)
            {
                result[10] += 1;
                for (int j = 1; j < 7; j++)
                {
                    if (array[i - j] == side) array[i - j] = -1;
                }
            }
            else if (flag){
                judge_continuous = 0;
                i = recover_i;
            }
        }
    }

    //12：死3
    default void analyse_three_dead(int []array, int side, int [] result){
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            int recover_i = i;
            boolean flag = true;
            if (array[i] == 1 - side)
            {
                i += 1;
                if (i > 13) break;
                int tempi = i + 3;
                while (i < tempi && flag)
                {
                    if (array[i] == side || array[i] == EMPTY)
                        judge_continuous += 1;
                    else if (array[i] == -1)
                    {
                        while (i < BOARD_SIZE - 1 && array[i + 1] != -1) i += 1;
                        judge_continuous = 0;
                        flag = false;
                    } else {
                        i = recover_i;
                        break;
                    }
                    i++;
                }
            }
            if (i < BOARD_SIZE && judge_continuous == 3 && array[i] == 1 - side && flag)
            {
                result[11] += 1;
                for (int j = 1; j < 4; j++)
                {
                    if (array[i - j] == side)
                        array[i - j] = -1;
                }
            } else if(flag) {
                judge_continuous = 0;
                i = recover_i;
            }
        }
    }

    //13：活2：__OO__
    default void analyse_two(int []array, int side, int [] result){
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            if (i > 2 && judge_continuous == 2)
            {
                if (array[i] == EMPTY && array[i - 3] == EMPTY)//两头为空
                {
                    result[12] += 1;//不需要跳出，因为可以同时存在两个活3，接下来继续判断
                    for (int j = 1; j < 3; j++)
                    {
                        if (array[i - j] == side)
                            array[i - j] = -1;
                    }
                }
            }
            if (array[i] == side)
                judge_continuous += 1;
            else
                judge_continuous = 0;
        }
    }

    //14：眠2：__O__O__
    default void analyse_two_sleep(int []array, int side, int [] result){
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            int recover_i = i;
            int judgeempty = 0;
            boolean flag = true;
            if (array[i] == side || array[i] == EMPTY)
            {
                if (array[i] == EMPTY)
                    judgeempty += 1;
                i += 1;
                judge_continuous += 1;
                if (i > 12) break;
                int tempi = i + 5;
                while(i < tempi && flag)
                {
                    if (array[i] == side || array[i] == EMPTY)
                    {
                        judge_continuous += 1;
                        if (array[i] == EMPTY)
                            judgeempty += 1;
                    }
                    else if (array[i] == -1)
                    {
                        while (i < BOARD_SIZE - 1 && array[i + 1] != -1) i += 1;
                        judge_continuous = 0;
                        flag = false;
                    } else {
                        i = recover_i;
                        break;
                    }
                    i++;
                }
            }
            if (judge_continuous == 6 && judgeempty == 4 && flag)
            {
                result[13] += 1;
                for (int j = 1; j < 7; j++)
                {
                    if (array[i - j] == side) array[i - j] = -1;
                }
            } else if(flag) {
                judge_continuous = 0;
                i = recover_i;
            }
        }
    }


    //15：死2
    default void analyse_two_dead(int []array, int side, int [] result) {
        int judge_continuous = 0;//判断值归零
        for (int i = 0; i < BOARD_SIZE; i++) {
            int recover_i = i;
            boolean flag = true;
            if (array[i] == 1 - side) {
                i += 1;
                if (i > 14) break;
                int tempi = i + 2;
                while (i < tempi && flag) {
                    if (array[i] == side || array[i] == EMPTY)
                        judge_continuous += 1;
                    else if (array[i] == -1) {
                        while (i < BOARD_SIZE - 1 && array[i + 1] != -1) i += 1;
                        judge_continuous = 0;
                        flag = false;
                    } else {
                        i = recover_i;
                        break;
                    }
                    i++;
                }
            }
            if (i < BOARD_SIZE && judge_continuous == 2 && array[i] == 1 - side && flag) {
                result[14] += 1;
                for (int j = 1; j < 3; j++) {
                    if (array[i - j] == side)
                        array[i - j] = -1;
                }
            } else if (flag) {
                judge_continuous = 0;
                i = recover_i;
            }
        }
    }

}
