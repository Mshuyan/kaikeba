# 条件过滤

+ 存储引擎层只会对用到索引的条件进行筛选，没有用到索引的字段的筛选工作是交给`MySqlServer`层进行筛选的

+ 例

  在`a`字段创建索引

  ```sql
  select a,b,c from t where a = 1 and d = 2;
  ```

  存储引擎层会使用`a`字段索引进行筛选，然后将查询结果交给`MySqlServer`层

  `MySqlServer`层再对`d=2`这个没有用到索引的条件进行筛选