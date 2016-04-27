<#import "email_master_template.html.ftl" as layout>

<@layout.masterTemplate>

<!-- define total width in px value -->
<table align="left" border="0" cellpadding="0" cellspacing="0" width="100%" class="mcnTextContentContainer" style="border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;">
    <tbody>
        <tr>

        <td valign="top" class="mcnTextContent" style="padding: 9px 18px;">

            <div style="padding: 9px 18px 0px;font-family: Raleway, 'Helvetica Neue', Helvetica, sans-serif;font-size: 24px;font-weight: normal;line-height: 100%;text-align: left;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;color: #606060;">
                Files for the las week
            </div>
            <div style="padding: 0px 18px 0px;font-family: PT Sans, 'Helvetica Neue', Helvetica, sans-serif;font-size: 14px;font-weight: normal;line-height: 150%;text-align: left;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;color: #606060;">
                <br>
                <br>
                    <table border="0">
                        <tr>
                            <td>Username:</td><td>${(username)!}</td>
                        </tr>
                        <tr>
                            <td>Number of files:</td><td>${(numFiles)!}</td>
                        </tr>
                    </table>
                <br>
            </div>
        </td>
        </tr>
    </tbody>
</table>

</@layout.masterTemplate>