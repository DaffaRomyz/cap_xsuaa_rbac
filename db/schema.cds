namespace model;

using { cuid, managed } from '@sap/cds/common';

entity Books : cuid, managed { 
  title  : String(111);
  author : Association to Authors;
  country : String(3);
  stock  : Integer;
  price  : Decimal(9,2);
}

entity Authors : cuid, managed {
  name   : String(111);
  country : String(3);
  is_active : Boolean default true;
  books  : Association to many Books on books.author = $self;
}