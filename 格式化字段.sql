-- 有两个 - 的，可处理

SELECT t1.table_name,t1.column_name
       ,REPLACE(col_name,SUBSTR(col_name,LOCATE('_',col_name),2),UPPER(SUBSTR(col_name,LOCATE('_',col_name)+1,1))) as last_name
       ,t1.column_comment
from   (
SELECT TABLE_NAME,COLUMN_NAME,COLUMN_COMMENT,REPLACE(LOWER(COLUMN_NAME),SUBSTR(LOWER(COLUMN_NAME),LOCATE('_',LOWER(COLUMN_NAME)),2),UPPER(SUBSTR(LOWER(COLUMN_NAME),LOCATE('_',LOWER(COLUMN_NAME))+1,1))) as col_name
FROM   information_schema.`COLUMNS`
where  TABLE_NAME = 'LOANCOLL_TB_CHANNEL_STRATEGY'
) t1
;


select 
 CONCAT(',',t.a,' AS ',t.b) AS c
 from (
SELECT t1.column_name as a
       ,REPLACE(col_name,SUBSTR(col_name,LOCATE('_',col_name),2),UPPER(SUBSTR(col_name,LOCATE('_',col_name)+1,1))) as b
from   (
SELECT TABLE_NAME,COLUMN_NAME,COLUMN_COMMENT,REPLACE(LOWER(COLUMN_NAME),SUBSTR(LOWER(COLUMN_NAME),LOCATE('_',LOWER(COLUMN_NAME)),2),UPPER(SUBSTR(LOWER(COLUMN_NAME),LOCATE('_',LOWER(COLUMN_NAME))+1,1))) as col_name
FROM   information_schema.`COLUMNS`
where  TABLE_NAME = 'ASSET_TB_BIZ_PROCESS_OPINION'
) t1
) t
;
