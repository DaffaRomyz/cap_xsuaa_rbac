sap.ui.require(
    [
        'sap/fe/test/JourneyRunner',
        'capxsuaarbacbooks/test/integration/FirstJourney',
		'capxsuaarbacbooks/test/integration/pages/BooksList',
		'capxsuaarbacbooks/test/integration/pages/BooksObjectPage'
    ],
    function(JourneyRunner, opaJourney, BooksList, BooksObjectPage) {
        'use strict';
        var JourneyRunner = new JourneyRunner({
            // start index.html in web folder
            launchUrl: sap.ui.require.toUrl('capxsuaarbacbooks') + '/index.html'
        });

       
        JourneyRunner.run(
            {
                pages: { 
					onTheBooksList: BooksList,
					onTheBooksObjectPage: BooksObjectPage
                }
            },
            opaJourney.run
        );
    }
);