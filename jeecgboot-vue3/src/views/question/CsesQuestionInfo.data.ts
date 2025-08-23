import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '删除人',
    align: "center",
    dataIndex: 'deleteBy'
  },
  {
    title: '删除日期',
    align: "center",
    dataIndex: 'deleteTime'
  },
  {
    title: '试题类型',
    align: "center",
    dataIndex: 'type'
  },
  {
    title: '环节',
    align: "center",
    dataIndex: 'segment'
  },
  {
    title: '版本',
    align: "center",
    dataIndex: 'version'
  },
  {
    title: '子版本',
    align: "center",
    dataIndex: 'subVersion'
  },
  {
    title: '场景',
    align: "center",
    dataIndex: 'scene'
  },
  {
    title: '子场景',
    align: "center",
    dataIndex: 'subScene'
  },
  {
    title: '权重',
    align: "center",
    dataIndex: 'weight'
  },
  {
    title: '试题文案',
    align: "center",
    dataIndex: 'content'
  },
];

// 高级查询数据
export const superQuerySchema = {
  deleteBy: {title: '删除人',order: 0,view: 'text', type: 'string',},
  deleteTime: {title: '删除日期',order: 1,view: 'datetime', type: 'string',},
  type: {title: '试题类型',order: 2,view: 'text', type: 'string',},
  segment: {title: '环节',order: 3,view: 'text', type: 'string',},
  version: {title: '版本',order: 4,view: 'text', type: 'string',},
  subVersion: {title: '子版本',order: 5,view: 'text', type: 'string',},
  scene: {title: '场景',order: 6,view: 'text', type: 'string',},
  subScene: {title: '子场景',order: 7,view: 'text', type: 'string',},
  weight: {title: '权重',order: 8,view: 'number', type: 'number',},
  content: {title: '试题文案',order: 9,view: 'text', type: 'string',},
};
