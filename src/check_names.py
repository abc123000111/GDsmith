import os


def get_java_files(directory):
	java_files = []
	for root, dirs, files in os.walk(directory):
		for f in files:
			if f.endswith('.java'):
				java_files.append(f)
	return java_files

def verify_prefix(prefix, files):
	if len(files) == 0:
		print(prefix + ' directory does not contain any files!')
		exit(-1)
	for f in files:
		if not f.startswith(prefix):
			print('The class name of ' + f + ' does not start with ' + prefix)
			exit(-1)

# TODO: ClickHouse (wait for https://github.com/gdsmith/gdsmith/pull/39)
verify_prefix('CockroachDB', get_java_files("gdsmith/cockroachdb/"))
verify_prefix('DuckDB', get_java_files("gdsmith/duckdb"))
verify_prefix('MariaDB', get_java_files("gdsmith/mariadb/"))
verify_prefix('MySQL', get_java_files("gdsmith/mysql/"))
verify_prefix('Postgres', get_java_files("gdsmith/postgres/"))
verify_prefix('SQLite3', get_java_files("gdsmith/sqlite3/"))
verify_prefix('TiDB', get_java_files("gdsmith/tidb/"))
