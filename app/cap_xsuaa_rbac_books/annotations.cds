using CatalogService as service from '../../srv/catalog-service';
annotate service.Books with @(
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Value : title,
            Label : 'title',
        },
        {
            $Type : 'UI.DataField',
            Value : author_ID,
            Label : 'author_ID',
        },
        {
            $Type : 'UI.DataField',
            Value : country,
            Label : 'country',
        },
        {
            $Type : 'UI.DataField',
            Value : price,
            Label : 'price',
        },
        {
            $Type : 'UI.DataField',
            Value : stock,
            Label : 'stock',
        },
        {
            $Type : 'UI.DataField',
            Value : createdAt,
        },
        {
            $Type : 'UI.DataField',
            Value : createdBy,
        },
        {
            $Type : 'UI.DataField',
            Value : modifiedAt,
        },
        {
            $Type : 'UI.DataField',
            Value : modifiedBy,
        },
    ],
    UI.Facets : [
        {
            $Type : 'UI.ReferenceFacet',
            Label : 'General',
            ID : 'General',
            Target : '@UI.FieldGroup#General',
        },
        {
            $Type : 'UI.ReferenceFacet',
            Label : 'Change Data',
            ID : 'ChangeData',
            Target : '@UI.FieldGroup#ChangeData',
        },
    ],
    UI.FieldGroup #General : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Value : title,
                Label : 'title',
            },
            {
                $Type : 'UI.DataField',
                Value : author_ID,
                Label : 'author_ID',
            },
            {
                $Type : 'UI.DataField',
                Value : country,
                Label : 'country',
            },
            {
                $Type : 'UI.DataField',
                Value : price,
                Label : 'price',
            },
            {
                $Type : 'UI.DataField',
                Value : stock,
                Label : 'stock',
            },
        ],
    },
    UI.FieldGroup #ChangeData : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Value : createdAt,
            },
            {
                $Type : 'UI.DataField',
                Value : createdBy,
            },
            {
                $Type : 'UI.DataField',
                Value : modifiedAt,
            },
            {
                $Type : 'UI.DataField',
                Value : modifiedBy,
            },
        ],
    },
    UI.CreateHidden          : {$edmJson: {$Not: {$Or: [
        {$Path: '/CatalogService.EntityContainer/Configuration/writeBooksOwn'},
        {$Or: [
            {$Path: '/CatalogService.EntityContainer/Configuration/writeBooksCountry'},
            {$Path: '/CatalogService.EntityContainer/Configuration/writeBooksAll'}
        ]}
    ]}}},
    UI.UpdateHidden          : {$edmJson: {$Not: {$Or: [
        {$And: [
            {$Path: '/CatalogService.EntityContainer/Configuration/writeBooksOwn'},
            {$Eq: [
                {$Path: 'createdBy'},
                {$Path: '/CatalogService.EntityContainer/Configuration/username'}
            ]}
        ]},
        {$Or: [
            {$And: [
                {$Path: '/CatalogService.EntityContainer/Configuration/writeBooksCountry'},
                {$Eq: [
                    {$Path: 'country'},
                    {$Path: '/CatalogService.EntityContainer/Configuration/country'}
                ]}
            ]},
            {$Path: '/CatalogService.EntityContainer/Configuration/writeBooksAll'}
        ]}
    ]}}},
    UI.DeleteHidden          : {$edmJson: {$Not: {$Or: [
        {$And: [
            {$Path: '/CatalogService.EntityContainer/Configuration/writeBooksOwn'},
            {$Eq: [
                {$Path: 'createdBy'},
                {$Path: '/CatalogService.EntityContainer/Configuration/username'}
            ]}
        ]},
        {$Or: [
            {$And: [
                {$Path: '/CatalogService.EntityContainer/Configuration/writeBooksCountry'},
                {$Eq: [
                    {$Path: 'country'},
                    {$Path: '/CatalogService.EntityContainer/Configuration/country'}
                ]}
            ]},
            {$Path: '/CatalogService.EntityContainer/Configuration/writeBooksAll'}
        ]}
    ]}}},
    Capabilities.DeleteRestrictions.Deletable : is_deletable
);

annotate service.Books with {
    author @(
        Common.ExternalID : author.name,
        Common.ValueList : {
            $Type : 'Common.ValueListType',
            CollectionPath : 'Authors',
            Parameters : [
                {
                    $Type : 'Common.ValueListParameterInOut',
                    LocalDataProperty : author_ID,
                    ValueListProperty : 'ID',
                },
            ],
            Label : 'Select an Author',
        },
        Common.ValueListWithFixedValues : false,
    )
};

