<div class="col-md-12">
    <div class="col-md-8">
        ${status.getText()}
    </div>

    <div class="col-md-4" id="tweet_${status.getId()}">
        <div class="col-md-10">
            <g:if test="${status.isRetweet()}">
                ${status.user.screenName} retweeted : @${status.getRetweetedStatus().user.screenName} - <g:formatDate format="MMM dd hh:mm"
                                                                                                                      date="${status.getCreatedAt()}"/>
            </g:if>
            <g:else>
                @${status.getUser().getScreenName()} - <g:formatDate format="MMM dd hh:mm" date="${status.getCreatedAt()}"/>
            </g:else>
        </div>
        <g:if test="${!status.isRetweeted() && validUser}">
            <div class="col-md-2">
                <a href="javascript:void(0);" onclick="loadRetweetModal(retweetUrl, this)"
                   data-statusId="${status.getId()}" data-statusText="${status.getText()}">
                    <asset:image src="reTweet.jpeg" width="20" height="15"/>
                </a>
            </div>
        </g:if>
    </div>
</div>

<script type="text/javascript">
    function loadRetweetModal(url, anchorTag) {
        $.ajax({
            url: url,
            type: 'post',
            data: { statusId: $(anchorTag).attr('data-statusId'), statusText: $(anchorTag).attr('data-statusText')},
            success: function (data) {
                console.log(data);
                $('body').append(data);
            }
        });
    }
</script>