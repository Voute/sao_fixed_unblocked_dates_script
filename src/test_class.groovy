class test_class {

    public static void main (String ... args)
    {
        def tf = new test_class();
        tf.calculate();
    }

    public Date[] calculate() {

        def fixDatesPerDay = new ArrayList();
        def failNotFixedDatesPerDay = new ArrayList();
        def unblockDatesPerDay = new ArrayList();
        def blockNotUnblockedDatesPerDay = new ArrayList();
        def fixedUnblockedDatesResult = new ArrayList();

        def fixed_dates = new ArrayList();
        def unblocked_dates = new ArrayList();
        def failed_dates = new ArrayList();
        def blocked_dates = new ArrayList();

        failed_dates.add(new Date(2017,10,1,10,40,00).getTime());
        fixed_dates.add (new Date(2017,10,1,10,41,00).getTime());
        failed_dates.add(new Date(2017,10,1,10,42,00).getTime());
        fixed_dates.add (new Date(2017,10,1,10,43,00).getTime());
        failed_dates.add(new Date(2017,10,1,10,44,00).getTime());
        fixed_dates.add (new Date(2017,10,1,10,45,00).getTime());

        failed_dates.add(new Date(2017,10,2,10,40,00).getTime());
        fixed_dates.add (new Date(2017,10,2,10,41,00).getTime());
        failed_dates.add(new Date(2017,10,2,10,42,00).getTime());

        blocked_dates.add  (new Date(2017,10,1,10,40,10).getTime());
        unblocked_dates.add(new Date(2017,10,1,10,41,10).getTime());
        blocked_dates.add  (new Date(2017,10,1,10,42,10).getTime());
        unblocked_dates.add(new Date(2017,10,1,10,43,10).getTime());
        blocked_dates.add  (new Date(2017,10,1,10,44,10).getTime());
        unblocked_dates.add(new Date(2017,10,1,10,45,10).getTime());

        blocked_dates.add  (new Date(2017,10,3,10,40,10).getTime());
        unblocked_dates.add(new Date(2017,10,3,10,41,10).getTime());
        blocked_dates.add  (new Date(2017,10,3,10,42,10).getTime());

//        def fixed_dates = doc['fixed_when_timestamps'];
//        def unblocked_dates = doc['unblocked_when_timestamps'];
//        def failed_dates = doc['failed_when_timestamps'];
//        def blocked_dates = doc['blocked_when_timestamps'];


        if (!unblocked_dates.isEmpty()) {

            def previousBlockItemYear;
            def previousBlockItemMonth;
            def previousBlockItemDay;

            if (unblocked_dates.size < blocked_dates.size)
            {
                Calendar lastBlockCalendar = Calendar.getInstance(TimeZone.getDefault());
                def lastBlockDate = new Date(blocked_dates.get(blocked_dates.size - 1));
                lastBlockCalendar.setTime(lastBlockDate);

                previousBlockItemYear = lastBlockCalendar.get(Calendar.YEAR);
                previousBlockItemMonth = lastBlockCalendar.get(Calendar.MONTH);
                previousBlockItemDay = lastBlockCalendar.get(Calendar.DAY_OF_MONTH);

                blockNotUnblockedDatesPerDay.add(0, lastBlockDate)
            }


            for (def i = unblocked_dates.size - 1; i >= 0; i--) {

                Calendar unblockedCalendar = Calendar.getInstance(TimeZone.getDefault());
                def unblockedDate = new Date(unblocked_dates.get(i));
                unblockedCalendar.setTime(unblockedDate);

                Calendar blockedCalendar = Calendar.getInstance(TimeZone.getDefault());
                def blockedDate = new Date(blocked_dates.get(i));
                blockedCalendar.setTime(blockedDate);

                if (! (
                        blockedCalendar.get(Calendar.DAY_OF_MONTH) == previousBlockItemDay &&
                            blockedCalendar.get(Calendar.MONTH) == previousBlockItemMonth &&
                            blockedCalendar.get(Calendar.YEAR) == previousBlockItemYear
                )  ) {

                    previousBlockItemYear = blockedCalendar.get(Calendar.YEAR);
                    previousBlockItemMonth = blockedCalendar.get(Calendar.MONTH);
                    previousBlockItemDay = blockedCalendar.get(Calendar.DAY_OF_MONTH);

                    if (unblockedCalendar.get(Calendar.DAY_OF_MONTH) == previousBlockItemDay &&
                            unblockedCalendar.get(Calendar.MONTH) == previousBlockItemMonth &&
                            unblockedCalendar.get(Calendar.YEAR) == previousBlockItemYear &&
                            unblockedCalendar.getTimeInMillis() >= blockedCalendar.getTimeInMillis()
                    ) {


                    } else {
                        blockNotUnblockedDatesPerDay.add(0, blockedDate);
                    }

                    unblockDatesPerDay.add(0, unblockedDate);

                }
            }

        }


        println blockNotUnblockedDatesPerDay.toString();
        println unblockDatesPerDay.toString();










        if (!blocked_dates.isEmpty()) {

            Calendar previousBlockedDate = null;
            Calendar previoustUnblockedDate = null;
            boolean previousUnblockedSameDay = false;

            for (def i = 0; i < blocked_dates.size(); i++) {

                Calendar blockedCalendar = Calendar.getInstance(TimeZone.getDefault());
                def blockedDate = new Date(blocked_dates.get(i));
                blockedCalendar.setTime(blockedDate);

                if (i < unblocked_dates.size()) {

                    Calendar unblockedCalendar = Calendar.getInstance(TimeZone.getDefault());
                    def unblockedDate = new Date(unblocked_dates.get(i));
                    unblockedCalendar.setTime(unblockedDate);

                    if (unblockedCalendar.get(Calendar.DAY_OF_MONTH) == blockedCalendar.get(Calendar.DAY_OF_MONTH) &&
                            unblockedCalendar.get(Calendar.MONTH) == blockedCalendar.get(Calendar.MONTH) &&
                            unblockedCalendar.get(Calendar.YEAR) == blockedCalendar.get(Calendar.YEAR) &&
                            unblockedCalendar.getTimeInMillis() >= blockedCalendar.getTimeInMillis()
                    ) {
                        if (i + 1 == blocked_dates.size)
                        {
                            unblockDatesPerDay.add(unblockedDate);

                        } else {
                            previoustUnblockedDate = unblockedCalendar;
                            previousUnblockedSameDay = true;
                        }

                    } else {

                        blockNotUnblockedDatesPerDay.add(blockedDate);
                        previoustUnblockedDate = unblockedCalendar;
                    }

                } else {
                    blockNotUnblockedDatesPerDay.add(blockedDate);
                }
            }



        }









        if (!blocked_dates.isEmpty()) {

            boolean isLastBlockInLoopUnblocked = true;
            boolean firstItem = true;
            def blockedDay = 0;
            def blockedMonth = 0;
            def blockedYear = 0;

            for (def i = 0; i < blocked_dates.size(); i++) {
                Calendar blockedCalendar = Calendar.getInstance(TimeZone.getDefault());
                Calendar unblockedCalendar = Calendar.getInstance(TimeZone.getDefault());
                def blockedDate = new Date(blocked_dates.get(i));
                blockedCalendar.setTime(blockedDate);

                if (!firstItem) {
                    if (!(blockedCalendar.get(Calendar.DAY_OF_MONTH) == blockedDay &&
                            blockedCalendar.get(Calendar.MONTH) == blockedMonth &&
                            blockedCalendar.get(Calendar.YEAR) == blockedYear)
                    ) {
                        if (isLastBlockInLoopUnblocked) {
                            unblockDatesPerDay.add(new Date(unblocked_dates.get(i - 1)));

                        } else {
                            blockNotUnblockedDatesPerDay.add(new Date(blocked_dates.get(i - 1)));
                        }
                    }
                } else {
                    firstItem = false;
                }

                isLastBlockInLoopUnblocked = false;

                if (i < unblocked_dates.size()) {
                    unblockedCalendar = Calendar.getInstance(TimeZone.getDefault());
                    def unblockedDate = new Date(unblocked_dates.get(i));
                    unblockedCalendar.setTime(unblockedDate);

                    blockedDay = blockedCalendar.get(Calendar.DAY_OF_MONTH);
                    blockedMonth = blockedCalendar.get(Calendar.MONTH);
                    blockedYear = blockedCalendar.get(Calendar.YEAR);

                    if (unblockedCalendar.get(Calendar.DAY_OF_MONTH) == blockedDay &&
                            unblockedCalendar.get(Calendar.MONTH) == blockedMonth &&
                            unblockedCalendar.get(Calendar.YEAR) == blockedYear &&
                            unblockedCalendar.getTimeInMillis() >= blockedCalendar.getTimeInMillis()
                    ) {
                        if (i + 1 == blocked_dates.size()) {
                            unblockDatesPerDay.add(unblockedDate);
                        } else {
                            isLastBlockInLoopUnblocked = true;
                        }

                    } else if (unblockedCalendar.getTimeInMillis() >= blockedCalendar.getTimeInMillis()) {
                        isLastBlockInLoopUnblocked = true;
                    }

                } else  // this is the last block
                {
                    blockNotUnblockedDatesPerDay.add(blockedDate);
                }

            }
        }

        if (!failed_dates.isEmpty()) {

            Calendar fixedCalendar = null;
            def fixedDate = null;

            boolean isLastFailInLoopFixed = false;
            def fixedDay = 0;
            def fixedMonth = 0;
            def fixedYear = 0;

            for (def i = 0; i < failed_dates.size(); i++) {
                Calendar failedCalendar = Calendar.getInstance(TimeZone.getDefault());
                def failedDate = new Date(failed_dates.get(i));
                failedCalendar.setTime(failedDate);

                if (!(failedCalendar.get(Calendar.DAY_OF_MONTH) == fixedDay &&
                        failedCalendar.get(Calendar.MONTH) == fixedMonth &&
                        failedCalendar.get(Calendar.YEAR) == fixedYear)
                ) {
                    if (isLastFailInLoopFixed) {
                        fixDatesPerDay.add(fixedDate);

                    }
                }

                isLastFailInLoopFixed = false;
                fixedCalendar = null;

                if (i < fixed_dates.size()) {
                    fixedCalendar = Calendar.getInstance(TimeZone.getDefault());
                    fixedDate = new Date(fixed_dates.get(i));
                    fixedCalendar.setTime(fixedDate);

                    fixedDay = fixedCalendar.get(Calendar.DAY_OF_MONTH);
                    fixedMonth = fixedCalendar.get(Calendar.MONTH);
                    fixedYear = fixedCalendar.get(Calendar.YEAR);

                    if (failedCalendar.get(Calendar.DAY_OF_MONTH) == fixedDay &&
                            failedCalendar.get(Calendar.MONTH) == fixedMonth &&
                            failedCalendar.get(Calendar.YEAR) == fixedYear &&
                            failedCalendar.getTimeInMillis() <= fixedCalendar.getTimeInMillis()
                    ) {
                        if (i + 1 == failed_dates.size())  // if this is the last fail
                        {
                            fixDatesPerDay.add(fixedDate);
                        } else {
                            isLastFailInLoopFixed = true;
                        }

                    } else if (failedCalendar.getTimeInMillis() <= fixedCalendar.getTimeInMillis()) {
                        isLastFailInLoopFixed = true;
                    }

                }

            }
        }

        for (def i = 0; i < fixDatesPerDay.size(); i++) {
            boolean noBlockForDay = true;

            Calendar fixedCalendar = Calendar.getInstance(TimeZone.getDefault());
            def fixedDate = fixDatesPerDay.get(i);
            fixedCalendar.setTime(fixedDate);

            for (def n = 0; n < blockNotUnblockedDatesPerDay.size(); n++) {
                Calendar blockCalendar = Calendar.getInstance(TimeZone.getDefault());
                def blockDate = blockNotUnblockedDatesPerDay.get(n);
                blockCalendar.setTime(blockDate);

                if (blockCalendar.get(Calendar.DAY_OF_MONTH) == fixedCalendar.get(Calendar.DAY_OF_MONTH) &&
                        blockCalendar.get(Calendar.MONTH) == fixedCalendar.get(Calendar.MONTH) &&
                        blockCalendar.get(Calendar.YEAR) == fixedCalendar.get(Calendar.YEAR)
                ) {
                    noBlockForDay = false;
                    break;
                }
            }

            if (noBlockForDay) {
                fixedUnblockedDatesResult.add(fixedDate);
            }
        }

        for (def i = 0; i < unblockDatesPerDay.size(); i++) {
            boolean noFailForDay = true;

            Calendar unblockedCalendar = Calendar.getInstance(TimeZone.getDefault());
            def unblockDate = unblockDatesPerDay.get(i);
            unblockedCalendar.setTime(unblockDate);

            for (def n = 0; n < failNotFixedDatesPerDay.size(); n++) {
                Calendar failCalendar = Calendar.getInstance(TimeZone.getDefault());
                def failDate = failNotFixedDatesPerDay.get(n);
                failCalendar.setTime(failDate);

                if (failCalendar.get(Calendar.DAY_OF_MONTH) == unblockedCalendar.get(Calendar.DAY_OF_MONTH) &&
                        failCalendar.get(Calendar.MONTH) == unblockedCalendar.get(Calendar.MONTH) &&
                        failCalendar.get(Calendar.YEAR) == unblockedCalendar.get(Calendar.YEAR)
                ) {
                    noFailForDay = false;
                    break;
                }
            }

            if (noFailForDay) {
                boolean noFixForDay = true;

                for (def n = 0; n < unblockDatesPerDay.size(); n++) {
                    Calendar fixedCalendar = Calendar.getInstance(TimeZone.getDefault());
                    def fixedDate = fixedUnblockedDatesResult.get(n);
                    fixedCalendar.setTime(fixedDate);

                    if (fixedCalendar.get(Calendar.DAY_OF_MONTH) == unblockedCalendar.get(Calendar.DAY_OF_MONTH) &&
                            fixedCalendar.get(Calendar.MONTH) == unblockedCalendar.get(Calendar.MONTH) &&
                            fixedCalendar.get(Calendar.YEAR) == unblockedCalendar.get(Calendar.YEAR)
                    ) {
                        if (fixedCalendar.getTimeInMillis() >= unblockedCalendar.getTimeInMillis()) {
                            noFixForDay = false;
                            break;
                        } else {
                            fixedUnblockedDatesResult.remove(n);
                            noFixForDay = true;
                            break;
                        }
                    }
                }
                if (noFixForDay) {
                    fixedUnblockedDatesResult.add(unblockDate);
                }
            }
        }

        return fixedUnblockedDatesResult;
    }

}
