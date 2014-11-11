#
# Copyright 2014 Atos
# Contact: Atos <roman.sosa@atos.net>
#
#    Licensed under the Apache License, Version 2.0 (the "License");
#    you may not use this file except in compliance with the License.
#    You may obtain a copy of the License at
#
#        http://www.apache.org/licenses/LICENSE-2.0
#
#    Unless required by applicable law or agreed to in writing, software
#    distributed under the License is distributed on an "AS IS" BASIS,
#    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#    See the License for the specific language governing permissions and
#    limitations under the License.
#

if [ "$0" != "bin/load-nuro-samples.sh" ]; then
	echo "Must be executed from project root"
	exit 1
fi

if [ $# -lt 2 ]; then
	echo "Usage: $0 <application_id> <entity_id>"
	exit 1
fi

SLA_MANAGER_URL="http://localhost:8080/sla-service"

curl -X POST $SLA_MANAGER_URL/providers -d@"samples/provider-nuro.xml" -H"Content-type: application/xml" -u user:password

curl -X POST $SLA_MANAGER_URL/templates -d@"samples/template-nuro.xml" -H"Content-type: application/xml" -u user:password

AGREEMENT=$(mktemp --tmpdir agreement-nuro.XXX)

cp samples/agreement-nuro.xml $AGREEMENT

sed -i -e "s/\${application}/$1/" -e "s/\${entity}/$2/" "$AGREEMENT"

curl -X POST $SLA_MANAGER_URL/agreements -d@"$AGREEMENT" -H"Content-type: application/xml" -u user:password

curl -X PUT $SLA_MANAGER_URL/enforcements/$1/start -u user:password