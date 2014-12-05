<div class="col-md-8">
    <div class="panel panel-primary">
        <div class="panel-heading">
            IG Post
        </div>

        <table class="table table-striped table-hover table-condensed table-bordered">
            <tbody>
            <g:each in="${statusUpdates}" var="status" status="i">
                <tr class="${(i % 2) == 0 ? 'alternate' : ''}">
                    <td><t:tweet status="${status}" userAuthenticated="${accessToken}"/></td>
                </tr>
            </g:each>

            </tbody>
        </table>
    </div>
</div>

