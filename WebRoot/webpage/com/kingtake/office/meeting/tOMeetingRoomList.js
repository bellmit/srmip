var li_east = 0;
//查看类型链接
function viewMeetiingInfo(id){
    var title = '查看会议室使用情况';
    if(li_east == 0){
        $('#main_meeting_list').layout('expand','east');
    }
    $('#main_meeting_list').layout('panel','east').panel('setTitle', title);
    $('#useListpanel').panel("refresh", "tOMeetingController.do?goMeetingUserInfo&meetingRoomId=" + id);
}