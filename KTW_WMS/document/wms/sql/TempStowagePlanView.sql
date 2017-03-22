create or replace view TEMP_STOWAGE_PLAN_VIEW as
(select
min(sto.ID) AS ID
,sto.STOWAGE_NUMBER
,sto.MAIN_DRIVER
,sto.CAR_NUMBER
,sto.JDE_BILL_NO
,sto.TMS_PLATFORM_CD

from
TEMP_STOWAGE sto

inner join
(select
T.STOWAGE_NUMBER,
max(T.UPDATE_TIME) AS UPDATE_TIME
from
(select 
ts.STOWAGE_NUMBER
,ts.EXE_TYPE
,min( ts.UPDATE_TIME ) AS UPDATE_TIME

from
TEMP_STOWAGE ts

where
ts.status = 0

group by
ts.STOWAGE_NUMBER
,ts.EXE_TYPE
)T
group by
T.STOWAGE_NUMBER
)M
on
sto.STOWAGE_NUMBER = M.STOWAGE_NUMBER
and
sto.UPDATE_TIME >= M.UPDATE_TIME

where 
sto.EXE_TYPE <> 'D'
and
sto.status = 0

group by
sto.STOWAGE_NUMBER
,sto.MAIN_DRIVER
,sto.CAR_NUMBER
,sto.JDE_BILL_NO
,sto.TMS_PLATFORM_CD
)