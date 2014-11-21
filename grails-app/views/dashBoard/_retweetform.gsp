<%@ page import="com.tweetAmp.Category" %>
<div id="retweet-modal" class="modal fade in show" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div id="retweet-modal-header" class="modal-header">
                <button id="retweet-modal-close" class="close retweet-modal-close-modal-class"
                        data-dismiss="modal" onclick="removeRetweetModal();">x</button>

                <h3>Re-tweet</h3></div>

            <div class="modal-body" id="retweet-modal-body-main">
                <p id="retweet-modal-text">
                    ${statusText}
                </p>

                <div class="form-group">
                    <label class="control-label hidden-phone" for="categories">
                        <g:message code="category.users.label" default="Select Categories"/>

                    </label>
                    <select id="categories" name="categories" data-placeholder="Select categories..."
                            class="chosen-select form-control" multiple
                            style="width:350px">
                        <g:each in="${Category.list()}" var="categories">
                            <option value="${categories.id}">${categories.name}</option>
                        </g:each>
                    </select>
                </div>
            </div>

            <div class="modal-footer ">
                <g:link controller="dashBoard" action="reTweet" class="btn btn-primary text-bold">Retweet</g:link>
                <a href="javascript:void (0)" onclick="removeRetweetModal();" class="btn btn-primary text-bold">
                    Close
                </a>
            </div>

            %{--<p class="xxpadding f13 text-darkgrey successMsg hide">Updated successfully!</p>--}%
        </div>
    </div>
</div>

<div class="modal-backdrop in" id="retweet-modal-backdrop-modal"></div>

<script type="text/javascript">
    function removeRetweetModal() {
        $('#retweet-modal').remove();
        $('#retweet-modal-backdrop-modal').remove();
    }
    $("#categories").chosen();
</script>