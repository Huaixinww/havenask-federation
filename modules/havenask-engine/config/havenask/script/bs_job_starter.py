#!/bin/env python
import os
import sys
import json

def executCmd(cmd):
    print cmd
    os.system(cmd)

if __name__ == '__main__':
    if len(sys.argv) != 6:
        print "Usage: python start_bs_job.py config_path data_path work_path runtime_path index_name"
        sys.exit(1)
    config_path = sys.argv[1]
    data_path = sys.argv[2]
    work_path = sys.argv[3]
    runtime_path = sys.argv[4]
    index_name = sys.argv[5]

    final_work_path = os.path.join(work_path, index_name)
    if os.path.exists(final_work_path):
        os.system("rm -rf %s" % final_work_path)
    os.system("mkdir -p %s" % final_work_path)
    cmd = "/ha3_install/usr/local/bin/bs startjob -c %s/bizs -n %s -j local -m full -d %s -w %s -i %s -p 1 --documentformat=ha3" % (config_path, index_name, data_path, final_work_path, runtime_path)
    executCmd(cmd)