create or replace view WMS_LABOR_VIEW as(
select
labor.ID
,labor.WH_ID
,labor.NAME
,labor.CODE
,labor.DISABLED
,labor."TYPE"
,to_char(my_concat( lg.NAME))AS LG_NM
from
WMS_LABOR labor

left join
WMS_LG_LABOR lgl
on
labor.ID = lgl.LABOR_ID

left join
WMS_LABOR_GROUP lg
on
lgl.LABOR_GROUP_ID = lg.ID

group by
labor.ID
,labor.WH_ID
,labor.NAME
,labor.CODE
,labor.DISABLED
,labor."TYPE"
)