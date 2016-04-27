<#import "email_master_template.html.ftl" as layout>

<@layout.masterTemplate>


                    <table border="0">
                        <tr>
                            <td>Username:</td><td>${(username)!}</td>
                        </tr>
                        <tr>
                            <td>Number of files:</td><td>${(numFiles)!}</td>
                        </tr>
                    </table>

</@layout.masterTemplate>
