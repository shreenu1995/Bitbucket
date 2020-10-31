Repo=$1
appname=$2
build_id=$3
file_name=$4
image=$Repo$appname:$build_id
sed -i 's|{{app-name}}|'$appname'|g' $file_name
sed -i 's|{{id}}|'$image'|g' $file_name
sed -i 's|'$Repo$appname:$build_id'|'$Repo/$appname:$build_id'|g' $file_name
ansible-playbook kube.yml -i hosts --extra-vars "app=$appname build_number=$build_id"
