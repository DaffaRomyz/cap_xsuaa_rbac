sap.ui.require(
    [
        'sap/fe/test/JourneyRunner',
        'capxsuaarbacauthors/test/integration/FirstJourney',
		'capxsuaarbacauthors/test/integration/pages/AuthorsList',
		'capxsuaarbacauthors/test/integration/pages/AuthorsObjectPage'
    ],
    function(JourneyRunner, opaJourney, AuthorsList, AuthorsObjectPage) {
        'use strict';
        var JourneyRunner = new JourneyRunner({
            // start index.html in web folder
            launchUrl: sap.ui.require.toUrl('capxsuaarbacauthors') + '/index.html'
        });

       
        JourneyRunner.run(
            {
                pages: { 
					onTheAuthorsList: AuthorsList,
					onTheAuthorsObjectPage: AuthorsObjectPage
                }
            },
            opaJourney.run
        );
    }
);