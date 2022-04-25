const abbrToBuilding = {
    H1: '第一教学楼',
    H2: '第二教学楼',
    H3: '第三教学楼',
    H4: '第四教学楼',
    H5: '第五教学楼',
    H6: '第六教学楼',
    HGX: '光华楼西辅楼',
}
const buildingToAbbr = {
    第一教学楼: 'H1',
    第二教学楼: 'H2',
    第三教学楼: 'H3',
    第四教学楼: 'H4',
    第五教学楼: 'H5',
    第六教学楼: 'H6',
    光华楼西辅楼: 'HGX',
}
const periods = [
    '周日','周一','周二','周三','周四','周五','周六',
]
const semesters = ['春','秋']
const years = [
    '2010-2011',
    '2011-2012',
    '2012-2013',
    '2013-2014',
    '2014-2015',
    '2015-2016',
    '2016-2017',
    '2017-2018',
    '2018-2019',
    '2019-2020',
    '2020-2021',
    '2021-2022'
]
export default
{
    buildingToAbbr,
    abbrToBuilding,
    periods,
    semesters,
    years
}